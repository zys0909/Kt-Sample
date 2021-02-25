package com.group.dev.ui.fish

import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/25
 */
class FishDrawable : Drawable() {

    private val size = 50

    //鱼头半径
    val headRadius = size * 1f

    //鱼身长度
    private val bodyLength = size * 3.2f

    //寻找鱼鳍起始点坐标的线长
    private val findFinsLength = size * 0.9f

    //鱼鳍的长度
    private val finsLength = size * 1.3f

    //尾部大圆的半径
    private val bigCircleRadius = size * 0.7f

    //尾部中圆的半径
    private val middleCircleRadius = size * 0.6f

    //尾部小圆的半径
    private val smallCircleRadius = size * 0.1f

    //寻找尾部中园圆心的线长
    private val findMiddleCircleLength = size * (1f + 0.6f)

    //寻找尾部小圆圆心的线长
    private val findSmallCircleLength = size * (2f + 0.4f) * 0.42f

    //寻找大三角形底边中心点的线长
    private val findTriangleLength = size * 2.7f * 0.42f

    private val path by lazy { Path() }
    private val paint by lazy { Paint() }

    private val fishAlpha = 110

    private val bodyAlpha = 160

    //鱼的重心
    val centerP = PointF(size * 4.19f, size * 4.19f)

    var headerPoint = PointF()

    //鱼的主要朝向
    var fishMainAngle = 90f

    private var currentValue = 0f
    var frequence = 1f

    init {
        with(paint) {
            //画笔类型 填充
            style = Paint.Style.FILL
            //抗锯齿
            isAntiAlias = true
            //防抖
            isDither = true
            setARGB(fishAlpha, 244, 92, 71)
        }

        val valueAnimator = ValueAnimator.ofFloat(0f, 3600f)
        with(valueAnimator) {
            //
            duration = 15 * 1000
            //
            repeatMode = ValueAnimator.RESTART
            //
            repeatCount = ValueAnimator.INFINITE
            //
            interpolator = LinearInterpolator()
            //
            addUpdateListener {
                currentValue = it.animatedValue as Float
                invalidateSelf()
            }
            valueAnimator.start()
        }
    }

    override fun draw(canvas: Canvas) {
        //鱼头的周期性偏移角度
        val offsetAngle = Math.toRadians(currentValue * 1.2 * frequence) * 10
        val fishAngle = (fishMainAngle + sin(offsetAngle)).toFloat()

        //鱼头的圆心
        headerPoint = calculatePoint(centerP, bodyLength / 2f, fishAngle)
        canvas.drawCircle(headerPoint.x, headerPoint.y, headRadius, paint)

        //画 右鱼鳍
        val rightFinsP = calculatePoint(headerPoint, findFinsLength, fishAngle - 110)
        makeFins(canvas, rightFinsP, fishAngle, true)

        //画 左鱼鳍
        val leftFinsP = calculatePoint(headerPoint, findFinsLength, fishAngle + 110)
        makeFins(canvas, leftFinsP, fishAngle, false)

        val bodyBottomCenterP = calculatePoint(headerPoint, bodyLength, fishAngle - 180)
        //画节肢1
        val middleCenterP = makeSegment(
            canvas, bodyBottomCenterP, bigCircleRadius, middleCircleRadius,
            findMiddleCircleLength, fishAngle, true
        )
        // 画节肢2
        makeSegment(
            canvas, middleCenterP, middleCircleRadius, smallCircleRadius,
            findSmallCircleLength, fishAngle, false
        )

        val fishEdgeLength =
            abs(sin(Math.toRadians(currentValue * 1.5 * frequence)) * bigCircleRadius).toFloat()
        // 画鱼尾
        makeTriangle(canvas, middleCenterP, findTriangleLength, fishEdgeLength, fishAngle)
        makeTriangle(canvas, middleCenterP, findTriangleLength - 10, fishEdgeLength - 20, fishAngle)

        //画身体
        makeBody(canvas, headerPoint, bodyBottomCenterP, fishAngle)
    }

    /**
     *
     */
    private fun makeBody(canvas: Canvas, headerP: PointF, bodyP: PointF, fishAngle: Float) {
        //身体的四个点
        val topLeftP = calculatePoint(headerP, headRadius, fishAngle + 90)
        val topRightP = calculatePoint(headerP, headRadius, fishAngle - 90)

        val bottomLeftP = calculatePoint(bodyP, bigCircleRadius, fishAngle + 90)
        val bottomRightP = calculatePoint(bodyP, bigCircleRadius, fishAngle - 90)

        // 二阶贝塞尔曲线的控制点 --- 决定鱼的胖瘦
        val controlLeftP = calculatePoint(headerP, bodyLength * 0.56f, fishAngle + 130)
        val controlRightP = calculatePoint(headerP, bodyLength * 0.56f, fishAngle - 130)

        path.reset()
        path.moveTo(topLeftP.x, topLeftP.y)
        path.quadTo(controlLeftP.x, controlLeftP.y, bottomLeftP.x, bottomLeftP.y)
        path.lineTo(bottomRightP.x, bottomRightP.y)
        path.quadTo(controlRightP.x, controlRightP.y, topRightP.x, topRightP.y)
        paint.alpha = bodyAlpha
        canvas.drawPath(path, paint)

    }

    /**
     * 画鱼尾  三角形
     */
    private fun makeTriangle(
        canvas: Canvas, startP: PointF, findCenterLength: Float,
        findEdgeLength: Float, fishAngle: Float
    ) {
        //
        val triangleAngle =
            (fishAngle + sin(Math.toRadians(currentValue * 1.5 * frequence)) * 35).toFloat()

        val edgeCenterP = calculatePoint(startP, findCenterLength, triangleAngle - 180)

        val leftP = calculatePoint(edgeCenterP, findEdgeLength, triangleAngle + 90)
        val rightP = calculatePoint(edgeCenterP, findEdgeLength, triangleAngle - 90)

        path.reset()
        path.moveTo(startP.x, startP.y)
        path.lineTo(leftP.x, leftP.y)
        path.lineTo(rightP.x, rightP.y)
        canvas.drawPath(path, paint)
    }

    /**
     * 画节肢 梯形
     */
    private fun makeSegment(
        canvas: Canvas, bottomCenterP: PointF, bigRadius: Float, smallRadius: Float,
        findSmallCenterLength: Float, fishAngle: Float, hasBigCircle: Boolean
    ): PointF {
        //节肢的周期性偏移角度
        val segmentAngle = if (hasBigCircle) {
            (fishAngle + cos(Math.toRadians(currentValue * 1.5 * frequence)) * 15).toFloat()
        } else {
            (fishAngle + sin(Math.toRadians(currentValue * 1.5 * frequence)) * 35).toFloat()
        }
        //梯形上底的中心
        val upperCenterP = calculatePoint(bottomCenterP, findSmallCenterLength, segmentAngle - 180)

        //梯形的四个顶点
        val bottomLeftP = calculatePoint(bottomCenterP, bigRadius, segmentAngle + 90)
        val bottomRightP = calculatePoint(bottomCenterP, bigRadius, segmentAngle - 90)
        val upperLeftP = calculatePoint(upperCenterP, smallRadius, segmentAngle + 90)
        val upperRightP = calculatePoint(upperCenterP, smallRadius, segmentAngle - 90)
        //画大圆
        if (hasBigCircle) {
            canvas.drawCircle(bottomCenterP.x, bottomCenterP.y, bigRadius, paint)
        }
        //画小圆
        canvas.drawCircle(upperCenterP.x, upperCenterP.y, smallRadius, paint)
        //画梯形
        path.reset()
        path.moveTo(upperLeftP.x, upperLeftP.y)
        path.lineTo(upperRightP.x, upperRightP.y)
        path.lineTo(bottomRightP.x, bottomRightP.y)
        path.lineTo(bottomLeftP.x, bottomLeftP.y)
        canvas.drawPath(path, paint)
        return upperCenterP
    }

    /**
     * 画鱼鳍
     */
    private fun makeFins(canvas: Canvas, startP: PointF, fishAngle: Float, isRight: Boolean) {
        //鱼鳍的方向控制角度
        val controlAngle = 115
        //鱼鳍的终点 --- 二阶贝塞尔曲线的终点
        val endP = calculatePoint(startP, finsLength, fishAngle - 180)
        //二阶贝塞尔曲线的控制点
        val controlP = if (isRight) {
            calculatePoint(startP, finsLength * 1.8f, fishAngle - controlAngle)
        } else {
            calculatePoint(startP, finsLength * 1.8f, fishAngle + controlAngle)
        }
        //绘制
        path.reset()
        path.moveTo(startP.x, startP.y)
        //二阶贝塞尔曲线
        path.quadTo(controlP.x, controlP.y, endP.x, endP.y)
        canvas.drawPath(path, paint)
    }

    /**
     * 根据起点 线长 和角度 计算 终点坐标
     * @param startPointF   起始点坐标
     * @param length        要求的点到起始点的直线距离 -- 线长
     * @param angle         偏移角度
     */
    private fun calculatePoint(startPointF: PointF, length: Float, angle: Float): PointF {
        return MathUtil.calculatePoint(startPointF, length, angle)
    }

    /**
     * 设置透明度
     */
    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    /**
     * 设置颜色过滤器，每个像素都会都被改变
     */
    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    /**
     * @see PixelFormat.OPAQUE  完全不透明，遮盖底下的所有内容
     * @see PixelFormat.TRANSPARENT 透明，不显示任何内容
     * @see PixelFormat.TRANSLUCENT 只有绘制的地方才覆盖底下的内容
     */
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicHeight(): Int {
        return (size * 8.38).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (size * 8.38).toInt()
    }
}