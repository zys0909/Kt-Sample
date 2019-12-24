package com.dev.zhaoys.ui.other

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import java.lang.ref.WeakReference
import java.util.*
import kotlin.math.*


/**
 * 描述:
 *
 * author zys
 * create by 2019-12-20
 */
class ClockView(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {
    constructor(context: Context) : this(context, null)

    var onDrawPoint: ((paint: Paint) -> Unit)? = null
    var onDrawCircle: ((paint: Paint) -> Unit)? = null
    var onDrawRuler1: ((paint: Paint) -> Unit)? = null
    var onDrawRuler2: ((paint: Paint) -> Unit)? = null
    var onDrawText: ((paint: Paint) -> Unit)? = null
    var onDrawNeedle1: ((paint: Paint) -> Unit)? = null
    var onDrawNeedle2: ((paint: Paint) -> Unit)? = null
    var onDrawNeedle3: ((paint: Paint) -> Unit)? = null

    private val smallAngle = (PI / 30f).toFloat()//每分钟的角度
    private val largeAngle = (PI / 6f).toFloat()//每小时的角度
    private val center: PointF = PointF()
    private val rulerPaint: Paint = Paint()//画刻度
    private val needlePaint: Paint = Paint()//画指针
    private val circlePaint: Paint = Paint()//画指针
    private var radius = 0f
    private var longRuler = dp2px(20)
    private var shortRuler = dp2px(5)

    private val tag = Paint()


    init {
        rulerPaint.isAntiAlias = true
        rulerPaint.strokeWidth = dp2px(1)
        rulerPaint.color = Color.BLACK
        rulerPaint.textSize = dp2px(18)
        rulerPaint.textAlign = Paint.Align.CENTER
        rulerPaint.style = Paint.Style.FILL

        needlePaint.isAntiAlias = true
        needlePaint.strokeWidth = dp2px(2)
        needlePaint.color = Color.BLACK

        circlePaint.isAntiAlias = true
        circlePaint.strokeWidth = dp2px(2)
        circlePaint.color = Color.BLACK

        tag.isAntiAlias = true
        tag.strokeWidth = dp2px(1)
        tag.color = Color.RED

    }



    private val autoTask = AutoTask(this)

    override fun onFinishInflate() {
        super.onFinishInflate()
        postDelayed(autoTask, 1000L)
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(autoTask)
        super.onDetachedFromWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val size = min(width, height)
        setMeasuredDimension(size, size)
        center.x = size / 2.0f
        center.y = size / 2.0f
        if (radius == 0f) {
            radius = center.x - dp2px(15)
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            circlePaint.style = Paint.Style.FILL
            onDrawPoint?.invoke(circlePaint)
            drawCircle(center.x, center.y, dp2px(3), circlePaint)
            circlePaint.style = Paint.Style.STROKE
            onDrawCircle?.invoke(circlePaint)
            drawCircle(center.x, center.y, center.x - dp2px(15), circlePaint)
            drawRuler(canvas, radius)
            drawPointH(canvas)
        }
    }

    private fun drawPointH(canvas: Canvas) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        val hourAngle = (hour % 12 + minute / 60f) * largeAngle
        val x = center.x + sin(hourAngle) * (radius - longRuler - dp2px(60))
        val y = center.y - cos(hourAngle) * (radius - longRuler - dp2px(60))
        onDrawNeedle1?.invoke(needlePaint)
        canvas.drawLine(center.x, center.y, x, y, needlePaint)

        val minuteAngle = minute * smallAngle
        val x1 = center.x + sin(minuteAngle) * (radius - longRuler - dp2px(40))
        val y1 = center.y - cos(minuteAngle) * (radius - longRuler - dp2px(40))
        onDrawNeedle2?.invoke(needlePaint)
        canvas.drawLine(center.x, center.y, x1, y1, needlePaint)

        val secondAngle = second * smallAngle
        val x2 = center.x + sin(secondAngle) * (radius - longRuler - dp2px(20))
        val y2 = center.y - cos(secondAngle) * (radius - longRuler - dp2px(20))
        onDrawNeedle3?.invoke(needlePaint)
        canvas.drawLine(center.x, center.y, x2, y2, needlePaint)
    }

    private fun drawRuler(canvas: Canvas, radius: Float) {
        rulerPaint.style = Paint.Style.FILL
        for (i in 1..60) {
            val x = center.x + sin(smallAngle * i) * radius
            val y = center.y - cos(smallAngle * i) * radius
            var x1: Float
            var y1: Float
            if (i % 5 == 0) {
                x1 = center.x + sin(smallAngle * i) * (radius - longRuler)
                y1 = center.y - cos(smallAngle * i) * (radius - longRuler)
                val xt = center.x + sin(smallAngle * i) * (radius - longRuler - dp2px(20))
                val yt = center.y - cos(smallAngle * i) * (radius - longRuler - dp2px(20))
                val fl = (rulerPaint.fontMetrics.ascent + rulerPaint.fontMetrics.descent) / 2f

                onDrawText?.invoke(rulerPaint)
                canvas.drawText(a2r(i / 5), xt, yt + abs(fl), rulerPaint)
                rulerPaint.strokeWidth = dp2px(3)
                onDrawRuler1?.invoke(rulerPaint)
                canvas.drawLine(x, y, x1, y1, rulerPaint)
            } else {
                x1 = center.x + sin(smallAngle * i) * (radius - shortRuler)
                y1 = center.y - cos(smallAngle * i) * (radius - shortRuler)
                rulerPaint.strokeWidth = dp2px(1)
                onDrawRuler2?.invoke(rulerPaint)
                canvas.drawLine(x, y, x1, y1, rulerPaint)
            }

        }
    }


    private fun dp2px(dp: Int) = Resources.getSystem().displayMetrics.density * dp

    private fun a2r(index: Int): String {
        var number = index
        var rNumber = ""
        val aArray = intArrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
        val rArray = arrayOf(
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X",
            "IX", "V", "IV", "I"
        )
        if (number < 1 || number > 3999) {
            rNumber = "-1"
        } else {
            for (i in aArray.indices) {
                while (number >= aArray[i]) {
                    rNumber += rArray[i]
                    number -= aArray[i]
                }
            }
        }
        return rNumber
    }

    class AutoTask(view: ClockView) : Runnable {

        private val weakReference = WeakReference(view)
        override fun run() {
            val view = weakReference.get()
            view?.apply {
                view.postInvalidate()
                view.invalidate()
                view.postDelayed(view.autoTask, 1000L)
            }
        }
    }
}


