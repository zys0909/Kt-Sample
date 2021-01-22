package com.group.dev

import android.app.Application
import com.group.dev.core.App

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/22
 */
class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        App.instance = this

    }
}