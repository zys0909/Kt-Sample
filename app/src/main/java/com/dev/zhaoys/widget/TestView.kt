package com.dev.zhaoys.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.group.dev.ext.dp
import com.group.dev.ext.dpf
import java.lang.ref.WeakReference

/**
 * 描述:
 *
 * author zys
 * create by 2019-12-19
 */
private const val TIME_AUTO_POLL: Long = 2000

class TestView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet, 0) {
    var position = 0
        set(value) {
            field = value
            invalidate()
        }
    private var running = false
    var autoPollTask: AutoPollTask =
        AutoPollTask(this)
    private val paint = Paint()
    private val drawable: GradientDrawable = GradientDrawable()

    init {
        paint.color = Color.RED
        paint.isAntiAlias = true
        paint.textSize = 20.dpf
        paint.textAlign = Paint.Align.CENTER

        drawable.setColor(Color.TRANSPARENT)
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setStroke(3.dp, Color.BLACK)
        drawable.cornerRadius = 3f
    }

    constructor(context: Context) : this(context, null)


    fun start() {
        if (running) stop()
        running = true
        postDelayed(autoPollTask, TIME_AUTO_POLL)
    }

    fun stop() {
        running = false
        removeCallbacks(autoPollTask)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val fl = height / 2.0f + paint.textSize / 2f
        canvas?.let {
            it.rotate(position * 15f, width / 2.0f, height / 2.0f)
            it.drawText(position.toString(), width / 2.0f, fl, paint)
        }
        canvas?.let {
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(it)
        }
    }

    class AutoPollTask(view: TestView) : Runnable {

        private val weakReference = WeakReference(view)
        override fun run() {
            val view = weakReference.get()
            if (view != null) {
                val pos = view.position + 1
                view.position = pos
                Log.i("测试TAG", "position = $pos")
                view.postDelayed(
                    view.autoPollTask,
                    TIME_AUTO_POLL
                )
            }
        }
    }
}