package com.dev.zhaoys.ui.other

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.dev.zhaoys.R
import com.dev.zhaoys.base.BaseActivity
import kotlinx.android.synthetic.main.activity_touch.*
import org.jetbrains.anko.toast

class TouchActivity : BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_touch

    override fun init(savedInstanceState: Bundle?) {
        button.setOnClickListener {
            toast("点击")
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("测试TAG", this.javaClass.simpleName + " - onTouchEvent")
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("测试TAG", this.javaClass.simpleName + " - dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }
}
