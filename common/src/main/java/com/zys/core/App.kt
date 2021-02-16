package com.zys.core

import android.app.Application
import kotlin.properties.Delegates

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/22
 */
object App {

    @JvmStatic
    @get:JvmName("instance")
    lateinit var instance: Application


    @JvmStatic
    @get:JvmName("debug")
    var debug by Delegates.notNull<Boolean>()


    fun init(application: Application, debug: Boolean) {
        instance = application
        this.debug = debug
    }
}