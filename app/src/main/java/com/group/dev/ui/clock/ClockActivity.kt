package com.group.dev.ui.clock

import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import com.group.dev.Value
import com.group.common.base.SimpleViewActivity
import com.group.common.core.ColorR
import java.util.*

/**
 * 描述:
 *
 * author zys
 * create by 2019-12-20
 */
class ClockActivity : SimpleViewActivity<FrameLayout>() {

    private val clock by lazy { ClockView(this) }
    private val r = Random()
    private val p: Int
        get() {
            return r.nextInt(1000)
        }

    override fun generateRootView(): FrameLayout = FrameLayout(this)

    override fun init(view: FrameLayout) {
        val layoutParams = FrameLayout.LayoutParams(-1, -1)
        layoutParams.gravity = Gravity.CENTER
        view.addView(clock, layoutParams)

        clock.setBackgroundColor(ColorR.EEEEEE)
        clock.onDrawPoint = {
            it.color = Color.parseColor(Value.color[p % 12])
        }
        clock.onDrawCircle = {
            it.color = Color.parseColor(Value.color[p % 12])
        }
        clock.onDrawRuler1 = {
            it.color = Color.parseColor(Value.color[p % 12])
        }
        clock.onDrawRuler2 = {
            it.color = Color.parseColor(Value.color[p % 12])
        }
        clock.onDrawNeedle1 = {
            it.color = Color.parseColor(Value.color[p % 12])
        }
        clock.onDrawNeedle2 = {
            it.color = Color.parseColor(Value.color[p % 12])
        }
        clock.onDrawNeedle3 = {
            it.color = Color.parseColor(Value.color[p % 12])
        }

    }
}