package com.group.dev.ui.fish

import android.widget.FrameLayout
import com.group.common.base.SimpleViewActivity

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/21
 */
class FishActivity : SimpleViewActivity<FrameLayout>() {
    override fun generateRootView(): FrameLayout {
        return FrameLayout(this)
    }

    override fun init(view: FrameLayout) {

    }
}