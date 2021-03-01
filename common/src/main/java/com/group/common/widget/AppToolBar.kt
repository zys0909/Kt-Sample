package com.group.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.group.common.R
import com.group.common.core.ColorR
import com.group.common.ext.activity
import com.group.common.ext.debounceClick
import com.group.common.ext.dp
import com.group.common.ext.fitSystemBar

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/15
 */
class AppToolBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {


    private val toolBar = Toolbar(context)
    private val backView = AppCompatImageView(context)

    init {
        setBackgroundColor(ColorR.EEEEEE)
        orientation = HORIZONTAL
        toolBar.apply {
            minimumHeight = 50.dp
        }
        backView.setPadding(10.dp)
        backView.setImageResource(R.drawable.ic_android_black_24dp)
        val backParams = LayoutParams(-2, -2)
        backParams.gravity = Gravity.CENTER
        backParams.marginStart = 10.dp
        addView(backView, backParams)

        val toolBarParams = LayoutParams(0, -2)
        toolBarParams.weight = 1f
        toolBarParams.marginStart = 10.dp
        addView(toolBar, toolBarParams)
        fitSystemBar()
        backView.debounceClick {
            it.activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    fun setTitle(titleChar: CharSequence, showBack: Boolean = true) {
        backView.isVisible = showBack
        toolBar.title = titleChar
        requestLayout()
    }
}