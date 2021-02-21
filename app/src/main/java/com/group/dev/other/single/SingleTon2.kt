package com.group.dev.other.single

/**
 * 描述:懒汉式
 *
 * author zhaoys
 * create by 2019/8/2 0002
 */
class SingleTon2 private constructor() {

    companion object {
        private var instance: SingleTon2? = null
            @Synchronized get() {
                if (field == null) {
                    field = SingleTon2()
                }
                return field
            }
    }

    fun doSomeWork() {
        //do some
    }

}