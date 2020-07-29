package com.dev.zhaoys.ui.other

import android.os.Bundle
import com.dev.zhaoys.R
import com.dev.zhaoys.app.BaseActivity

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

    }
}