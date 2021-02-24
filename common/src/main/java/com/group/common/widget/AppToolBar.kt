package com.group.common.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.group.common.R
import com.group.common.core.ColorR
import com.group.common.ext.*

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/15
 */
class AppToolBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {


    private val toolBar = AppCompatTextView(context)
    private val backView = AppCompatImageView(context)

    init {
        setBackgroundColor(ColorR.EEEEEE)
        orientation = HORIZONTAL
        toolBar.apply {
            minimumHeight = 50.dp
            textSize = 16.dpf
            setTextColor(Color.BLACK)
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

    fun setTitle(titleChar: CharSequence, homeAsUp: Boolean = true) {
        toolBar.isVisible = homeAsUp
        toolBar.text = titleChar
        requestLayout()
    }
}