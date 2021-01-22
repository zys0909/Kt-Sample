package com.group.dev.core

import android.app.Application
import com.dev.zhaoys.R

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
    val debug: Boolean
        get() = instance.resources.getBoolean(R.bool.debug_mode)
}