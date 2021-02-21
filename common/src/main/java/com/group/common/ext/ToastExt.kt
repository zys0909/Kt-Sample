package com.group.common.ext

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/15
 */
fun Context.toast(text: CharSequence?) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    } else {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Fragment.toast(text: CharSequence?) = requireContext().toast(text)