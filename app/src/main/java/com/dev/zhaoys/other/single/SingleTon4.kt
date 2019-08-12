package com.dev.zhaoys.other.single

import android.util.Log

/**
 * 描述:静态内部类单例
 *
 * author zhaoys
 * create by 2019/8/2 0002
 */
class SingleTon4 private constructor() {

    init {
        Log.d("测试TAG", "init == 2")
    }

    companion object {
        @JvmStatic
        val instance = SingleTonHolder.holder
    }


    fun doSomeWork() {
        Log.d("测试TAG", "do some")
    }

    private object SingleTonHolder {

        val holder = {
            Log.d("测试TAG", "李二狗")
            SingleTon4()
        }.invoke()
    }
}