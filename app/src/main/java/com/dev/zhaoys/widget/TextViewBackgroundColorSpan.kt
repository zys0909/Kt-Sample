package com.dev.zhaoys.widget

import android.graphics.*
import android.graphics.Paint.FontMetricsInt
import android.graphics.drawable.GradientDrawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ReplacementSpan
import android.widget.TextView
import androidx.annotation.ColorInt
import com.dev.zhaoys.extend.dp
import com.dev.zhaoys.extend.dpf

fun TextView.setSpan(
    text1: String,
    text2: String,
    @ColorInt textColor: Int = Color.WHITE,
    @ColorInt startColor: Int = Color.parseColor("#B4BAE3"),
    @ColorInt endColor: Int = Color.parseColor("#9698C4")
) {
    val ssb = SpannableStringBuilder()
    ssb.append(text1)
    ssb.setSpan(
        TextViewBackgroundColorSpan(textColor, startColor, endColor)
            .setPadding(t = 2.dp, b = 1.dp),
        0, text1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    ssb.append(text2)
    this.text = ssb
}

class TextViewBackgroundColorSpan(
    private val textColor: Int,
    private val startColor: Int,
    private val endColor: Int
) : ReplacementSpan() {
    private val outRect = Rect()

    fun setPadding(l: Int = 0, t: Int = 0, r: Int = 0, b: Int = 0): TextViewBackgroundColorSpan {
        outRect.set(l, t, r, b)
        return this
    }

    override fun getSize(
        paint: Paint, text: CharSequence, start: Int, end: Int, fm: FontMetricsInt?
    ): Int {
        paint.init()
        return (paint.measureText(text, start, end) + 10.dp).toInt()
    }

    private fun Paint.init() {
        color = textColor
        textSize = 12.dpf
        typeface = Typeface.DEFAULT
    }

    override fun draw(
        canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int,
        bottom: Int, paint: Paint
    ) {
        paint.init()
        //画圆角矩形背景
        val background = GradientDrawable()
        background.shape = GradientDrawable.RECTANGLE

        //设置线性渐变，除此之外还有：
        // GradientDrawable.SWEEP_GRADIENT（扫描式渐变）
        // GradientDrawable.RADIAL_GRADIENT（圆形渐变）
        background.gradientType = GradientDrawable.LINEAR_GRADIENT

        //圆角半径
        background.cornerRadius = 2.dpf
        //渐变方向从左到右
        background.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        //增加渐变效果需要使用setColors方法来设置颜色（中间可以增加多个颜色值）
        background.colors = intArrayOf(startColor, endColor)
        //背景范围
        val textWidth = paint.measureText(text, start, end).toInt() + 8.dp
        background.setBounds(
            x.toInt() + outRect.left,
            top + outRect.top,
            x.toInt() + textWidth + outRect.right,
            bottom - outRect.bottom
        )
        background.draw(canvas)
        //画文字,两边各增加4dp
        canvas.drawText(text, start, end, x + 4.dp, y - 2.dpf, paint)
        paint.reset()
    }

}