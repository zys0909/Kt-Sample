package com.group.dev.ui.hook

import android.graphics.Color
import android.os.Bundle
import com.group.dev.R
import com.group.dev.Value
import com.group.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_clock.*
import java.util.*

/**
 * 描述:
 *
 * author zys
 * create by 2020/9/11
 */
class HookActivity : BaseActivity() {
    private val r = Random()
    private val p: Int
        get() {
            return r.nextInt(1000)
        }

    override fun layoutId(): Int = R.layout.activity_clock

    override fun init(savedInstanceState: Bundle?) {
        initToolbar("Clock")
        clock.setBackgroundColor(Color.parseColor("#eeeeee"))
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