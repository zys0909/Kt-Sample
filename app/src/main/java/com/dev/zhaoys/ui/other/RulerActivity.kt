package com.dev.zhaoys.ui.other

import android.os.Bundle
import com.dev.zhaoys.R
import com.dev.zhaoys.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ruler.*

/**
 * 描述:
 *
 * author zys
 * create by 2019-11-22
 */
class RulerActivity :BaseActivity(){
    override fun layoutId(): Int = R.layout.activity_ruler

    override fun init(savedInstanceState: Bundle?) {
        ruler_view.setScope(0,1001,100)
        ruler_view.setCurrentItem("0")
    }
}