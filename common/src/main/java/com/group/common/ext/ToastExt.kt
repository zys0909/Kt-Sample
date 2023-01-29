package com.group.common.ext

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * 描述:Toast。扩展
 *
 * author zys
 * create by 2021/2/15
 */
private var toastVal: Toast? = null

fun Context.toast(text: CharSequence?) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        showToast(this, text)
    } else {
        Handler(Looper.getMainLooper()).post {
            showToast(this, text)
        }
    }
}

private fun showToast(context: Context, text: CharSequence?) {
    if (toastVal == null) {
        toastVal = Toast.makeText(context, text, Toast.LENGTH_SHORT)
    } else {
        toastVal?.setText(text)
    }
    toastVal?.show()
}

fun Fragment.toast(text: CharSequence?) = requireContext().toast(text)