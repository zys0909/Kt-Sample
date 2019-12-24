package com.dev.zhaoys.ui.other

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.dev.zhaoys.R
import com.dev.zhaoys.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ruler.*

/**
 * 描述:
 *
 * author zys
 * create by 2019-11-22
 */
class RulerActivity : BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_ruler

    override fun init(savedInstanceState: Bundle?) {
        initToolbar("Ruler")
        ruler_view.setScope(0, 1000, 100, 10)
        ruler_view.setScrollSelected { selected -> showToast(this, selected) }
    }

    private var toast: Toast? = null

    @SuppressLint("ShowToast")
    private fun showToast(context: Context, text: String) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(text)
        }
        toast!!.show()
    }


}