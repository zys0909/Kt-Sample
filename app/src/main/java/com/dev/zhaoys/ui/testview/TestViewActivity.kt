package com.dev.zhaoys.ui.testview

import android.os.Bundle
import com.dev.zhaoys.R
import com.dev.zhaoys.base.BaseActivity

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

    }
}