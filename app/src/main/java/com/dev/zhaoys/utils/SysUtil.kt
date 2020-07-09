package com.dev.zhaoys.utils

import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.dev.zhaoys.app.App

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

    fun getIP() {
        val cm = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE)
        if (cm is ConnectivityManager) {
            cm.activeNetworkInfo.subtype
        }

    }

}