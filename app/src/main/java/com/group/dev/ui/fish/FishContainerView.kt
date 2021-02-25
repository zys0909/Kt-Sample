package com.group.dev.ui.fish

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.addListener
import com.group.common.ext.dp
import kotlin.math.atan2

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/25
 */
@SuppressLint("AnimatorKeep")
class FishContainerView constructor(context: Context) : FrameLayout(context) {
    private val paint by lazy { Paint() }
    private val path by lazy { Path() }
    private val ivFish by lazy { AppCompatImageView(context) }
    private val fishDrawable by lazy { FishDrawable() }
    private val objectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "ripple", 0f, 1f)
    }

    private var touchX = 0f
    private var touchY = 0f
    var ripple = 0f
        set(value) {
            alpha = (100 * (1 - ripple)).toInt()
            field = value
        }
    private var alpha = 0

    init {
        setWillNotDraw(false)
        with(paint) {
            //画笔类型
            style = Paint.Style.STROKE
            strokeWidth = 8f
            //抗锯齿
            isAntiAlias = true
            //防抖
            isDither = true

        }



        ivFish.setImageDrawable(fishDrawable)
        addView(ivFish, 150.dp, 150.dp)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.alpha = alpha
        canvas?.drawCircle(touchX, touchY, ripple * 150, paint)
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        touchX = event?.x ?: 0f
        touchY = event?.y ?: 0f
        paint.alpha = 100
        objectAnimator.duration = 1000
        objectAnimator.start()
        makeTrail()
        return super.onTouchEvent(event)
    }

    private fun makeTrail() {
        // 鱼的重心：相对ImageView坐标
        val p = fishDrawable.centerP
        // 鱼的重心：绝对坐标 --- 起始点
        val fishCenter = PointF(ivFish.x + p.x, ivFish.y + p.y)
        // 鱼头圆心的坐标 -- 控制点1
        val headP =
            PointF(ivFish.x + fishDrawable.headerPoint.x, ivFish.y + fishDrawable.headerPoint.y)
        // 点击坐标 -- 结束点
        val touchP = PointF(touchX, touchY)
        val angle = includeAngle(fishCenter, headP, touchP) / 2
        val delta = includeAngle(fishCenter, PointF(fishCenter.x + 1, fishCenter.y), headP)

        // 控制点2 的坐标
        val controlP2 =
            MathUtil.calculatePoint(fishCenter, fishDrawable.headRadius * 1.6f, angle + delta)
        path.reset()
        path.moveTo(fishCenter.x - p.x, fishCenter.y - p.y)
        path.cubicTo(
            headP.x - p.x, headP.y - p.y,
            controlP2.x - p.x, controlP2.y - p.y,
            touchX - p.x, touchY - p.y
        )
        val animator2 = ObjectAnimator.ofFloat(ivFish, "x", "y", path)
        animator2.duration = 2000
        animator2.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                fishDrawable.frequence = 3f
            }

            override fun onAnimationEnd(animation: Animator?) {
                fishDrawable.frequence = 1f
            }
        })
        val pathMeasure = PathMeasure(path, false)
        val tan = FloatArray(2)
        animator2.addUpdateListener {
            val fraction = it.animatedFraction
            pathMeasure.getPosTan(pathMeasure.length * fraction, null, tan)
            val angle = Math.toDegrees(atan2(-tan[1], tan[0]).toDouble())
            fishDrawable.fishMainAngle = angle.toFloat()
        }
        animator2.start()
    }

    /**
     * 计算∠AoB 的值
     */
    private fun includeAngle(O: PointF, A: PointF, B: PointF): Float {
        return MathUtil.includeAngle(O, A, B)
    }
}