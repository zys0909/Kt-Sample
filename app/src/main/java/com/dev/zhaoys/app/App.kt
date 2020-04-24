package com.dev.zhaoys.app

import android.app.Application

/**
 * 描述:
 *
 * @author zhaoys
 * create by 2019/7/1 0001 10:39
 */
class App : Application() {
    companion object {
        @JvmStatic
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}