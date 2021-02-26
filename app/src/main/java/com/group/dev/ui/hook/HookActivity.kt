package com.group.dev.ui.hook

import android.widget.LinearLayout
import com.group.common.base.SimpleViewActivity

/**
 * 描述:
 *
 * author zys
 * create by 2020/9/11
 */
class HookActivity : SimpleViewActivity<LinearLayout>() {

    override fun generateRootView(): LinearLayout {

        return LinearLayout(this)
    }

    override fun init(view: LinearLayout) {

    }
}