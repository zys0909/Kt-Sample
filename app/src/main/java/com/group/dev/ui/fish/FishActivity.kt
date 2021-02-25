package com.group.dev.ui.fish

import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import com.group.common.base.SimpleViewActivity
import com.group.common.ext.dp

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/21
 */
class FishActivity : SimpleViewActivity<FrameLayout>() {
    private val imageView by lazy { AppCompatImageView(this) }
    override fun generateRootView(): FrameLayout {
        return FishContainerView(this)
    }

    override fun init(view: FrameLayout) {
//        imageView.setImageDrawable(FishDrawable())
//        view.addView(imageView, 150.dp, 150.dp)

    }
}