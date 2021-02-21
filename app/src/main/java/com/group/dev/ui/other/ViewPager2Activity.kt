package com.group.dev.ui.other

import android.os.Bundle
import com.group.dev.R
import com.group.common.base.BaseActivity

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