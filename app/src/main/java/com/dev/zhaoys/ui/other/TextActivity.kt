package com.dev.zhaoys.ui.other

import android.os.Bundle
import com.dev.zhaoys.R
import com.dev.zhaoys.app.BaseActivity
import com.dev.zhaoys.widget.setSpan
import kotlinx.android.synthetic.main.activity_text.*

/**
 * 描述:
 *
 * author zys
 * create by 2020/08/12
 */
class TextActivity : BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_text

    override fun init(savedInstanceState: Bundle?) {
        atv_name.setSpan("中原集团", "")
    }
}