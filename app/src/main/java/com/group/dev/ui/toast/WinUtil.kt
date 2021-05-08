package com.group.dev.ui.toast

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import com.group.common.core.App
import com.group.dev.R
import timber.log.Timber

/**
 * 描述:
 *
 * author zys
 * create by 2021/4/23
 */
object WinUtil {

    fun getWindowManager(): WindowManager {
        return App.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private val nm by lazy {
        App.instance.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }


    fun tryShow(activity: Activity) {
        try {
            createShow(activity)
        } catch (e: Exception) {
            e.logCat()
        }
    }

    private fun createShow(activity: Activity) {
        val view = LayoutInflater.from(activity).inflate(R.layout.layout_push_toast, null)
        val params = WindowManager.LayoutParams()
        with(params) {
            width = -2
            height = -2
            flags = (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        activity.windowManager.addView(view, params)
    }


    private fun Exception.logCat() {
        Timber.tag("测试TAG").i("logCat -> %s", message)
        for (s in stackTrace) {
            Timber.tag("测试TAG").i("logCat -> %s", s)
        }
    }
}