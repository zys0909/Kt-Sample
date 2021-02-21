package com.group.dev.ui.other

import android.os.Bundle
import com.group.dev.R
import com.group.common.base.BaseActivity
import com.group.dev.widget.setSpan
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