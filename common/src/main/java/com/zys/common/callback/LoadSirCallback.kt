@file:Suppress("UNUSED")

package com.zys.common.callback

import android.app.Application
import com.kingja.loadsir.callback.ProgressCallback
import com.kingja.loadsir.core.LoadSir

/**
 * 描述:LoadSirCallback
 *
 * author zys
 * create by 2020/08/11
 */
abstract class LoadSirCallback : com.kingja.loadsir.callback.Callback()

fun Application.initLoadSir() {
    LoadSir.beginBuilder()
        .addCallback(ErrorCallback()) //添加各种状态页
        .addCallback(EmptyCallback())
        .addCallback(LoadingCallback())
        .addCallback(ProgressCallback.Builder().build())
        .addCallback(SuccessCallback())
        .setDefaultCallback(LoadingCallback::class.java) //设置默认状态页
        .commit()
}