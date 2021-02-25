package com.group.common.base

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.group.common.core.ExtraConst
import com.group.common.widget.AppToolBar

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/21
 */
abstract class SimpleViewActivity<V : View> : AppCompatActivity() {
    private val defaultTitle: String by lazy {
        intent.getStringExtra(ExtraConst.ACTIVITY_TITLE) ?: ""
    }
    private val toolBar by lazy { AppToolBar(this) }
    private val rootView by lazy { generateRootView() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (defaultTitle.isNotEmpty()) {
            toolBar.setTitle(defaultTitle)
        }
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(toolBar, -1, -2)
        linearLayout.addView(rootView, -1, -1)
        init(rootView)
    }

    fun setToolBar(string: String) {
        toolBar.setTitle(string)
    }

    abstract fun generateRootView(): V

    abstract fun init(view: V)
}