package com.dev.zhaoys.app

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.zys.core.ColorR
import com.zys.core.ExtraConst
import com.zys.widget.AppToolBar

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/21
 */
abstract class SimpleViewActivity<V : View> : AppCompatActivity() {
    private val defaultTitle: String by lazy {
        intent.getStringExtra(ExtraConst.Pager_Title)
    }
    private val toolBar by lazy { AppToolBar(this) }
    private val rootView by lazy { generateRootView() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        toolBar.setBackgroundColor(ColorR.EEEEEE)
        if (defaultTitle.isNotEmpty()) {
            toolBar.title = defaultTitle
        }
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(toolBar, -1, -2)
        linearLayout.addView(rootView, -1, -1)
        init(rootView)
    }

    fun setToolBar(string: String) {
        toolBar.title = string
    }

    abstract fun generateRootView(): V

    abstract fun init(view: V)
}