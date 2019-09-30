package com.dev.zhaoys.ui.other

import android.os.Bundle
import com.dev.zhaoys.R
import com.dev.zhaoys.base.BaseActivity

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/9/3 0003
 */
class TestImageActivity : BaseActivity() {
    val url = "https://pic.centanet.com/guangzhou/pic/agent/56495.jpg"
    override fun layoutId(): Int = R.layout.activity_test_image

    override fun init(savedInstanceState: Bundle?) {
//        StringGlideEngine(this).load(icon_1, url, circleCrop = true)
//        TestGlideEngine(this).load(icon_2, url, circleCrop = true)
//        TestGlideEngine(this).load(icon_3, url, circleCrop = false)
    }
}