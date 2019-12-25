package com.dev.zhaoys.ui.other

import android.os.Bundle
import android.util.Log
import com.dev.zhaoys.R
import com.dev.zhaoys.base.BaseActivity
import java.text.DecimalFormat

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/9/19 0019
 */
class ViewPager2Activity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_viewpager2

    override fun init(savedInstanceState: Bundle?) {
        initToolbar("ViewPager2")
        initTTS()

    }

    private fun startAuto() {
    }

    private fun initTTS() {
        val a = Double.MAX_VALUE
        val d = DecimalFormat("#.0")
        val toDouble = d.format(a).toDouble()
        Log.i("测试TAG", toDouble.toString())
    }


}