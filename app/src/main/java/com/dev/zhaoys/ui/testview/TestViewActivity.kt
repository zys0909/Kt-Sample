package com.dev.zhaoys.ui.testview

import android.os.Bundle
import android.util.Log
import com.dev.zhaoys.R
import com.dev.zhaoys.base.BaseActivity
import com.yzy.voice.VoicePlay
import com.yzy.voice.util.StringUtils
import kotlinx.android.synthetic.main.activity_test_view.*
import java.text.DecimalFormat

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/9/19 0019
 */
class TestViewActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_test_view

    override fun init(savedInstanceState: Bundle?) {
        initToolbar("")
        initTTS()
        btn_play.setOnClickListener {
            startAuto()
        }
    }

    private fun startAuto() {
        val m =  StringUtils.getMoney("收到999.99元")
        VoicePlay.with(this).play(m)

    }

    private fun initTTS() {
        val a = Double.MAX_VALUE
        val d = DecimalFormat("#.0")
        val toDouble = d.format(a).toDouble()
        Log.i("测试TAG",toDouble.toString())
    }


}