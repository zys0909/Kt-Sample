package com.dev.zhaoys.extend

import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.FragmentActivity

/**
 * 描述:
 *
 * author zys
 * create by 2020/7/27
 */

fun Context.getActivity(): FragmentActivity? {
    var context: Context = this
    while (context is ContextWrapper) {
        if (context is FragmentActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}