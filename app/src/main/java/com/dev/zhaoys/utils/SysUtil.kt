package com.dev.zhaoys.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/7/24 0024
 */
object SysUtil {
    fun getActivity4Context(context: Context): AppCompatActivity? =
        when (context) {
            is AppCompatActivity -> context
            is ContextWrapper -> {
                getActivity4Context(context.baseContext)
            }
            else -> null
        }


}