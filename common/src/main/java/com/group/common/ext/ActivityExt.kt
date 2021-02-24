package com.group.common.ext

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

val Context.activity: AppCompatActivity?
    get() {
        var context: Context = this
        while (context is ContextWrapper) {
            if (context is AppCompatActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

val View.activity: AppCompatActivity?
    get() = this.context.activity