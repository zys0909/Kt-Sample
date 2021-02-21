package com.group.common.widget

import android.content.Context
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import com.group.common.ext.dp
import com.group.common.ext.fitSystemBar

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/15
 */
class AppToolBar(context: Context) : FrameLayout(context) {

    private val toolBar by lazy {
        Toolbar(context)
    }

    init {
        toolBar.apply {
            minimumHeight = 50.dp
        }
        addView(toolBar)
        fitSystemBar()
    }

    var title: CharSequence
        set(value) {
            toolBar.title = value
        }
        get() = toolBar.title
}