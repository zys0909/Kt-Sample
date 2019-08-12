package com.dev.zhaoys.other.single

/**
 * 描述:双重检测
 *
 * author zhaoys
 * create by 2019/8/2 0002
 */
class SingleTon3 private constructor() {

    companion object {
        val instance: SingleTon3 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SingleTon3()
        }
    }

    fun doSomeWork() {
        //do some
    }
}