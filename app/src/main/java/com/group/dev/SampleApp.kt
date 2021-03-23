package com.group.dev

import android.app.Application
import com.group.common.core.App
import timber.log.Timber

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/22
 */
class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        App.init(this,resources.getBoolean(R.bool.debug_mode))
        Timber.plant(Timber.DebugTree())
        App.init(this,true)

    }
}