package com.group.dev.ui.hook

import android.widget.FrameLayout
import com.group.common.base.SimpleViewActivity

/**
 * 描述:
 *
 * author zys
 * create by 2020/9/11
 */
class TargetActivity : SimpleViewActivity<FrameLayout>() {


    override fun generateRootView(): FrameLayout {
        return FrameLayout(this)
    }

    override fun init(view: FrameLayout) {

    }
}