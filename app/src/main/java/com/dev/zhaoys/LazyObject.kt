package com.dev.zhaoys

import androidx.lifecycle.MutableLiveData


/**
 * 描述:
 *
 * author zys
 * create by 2020/8/24
 */
fun <T> simpleLiveData(): Lazy<MutableLiveData<T>> {
    return LazyObject {
        MutableLiveData<T>()
    }
}

/**
 * 委托对象
 */
class LazyObject<M>(private val block: () -> M) : Lazy<M> {

    private var cached: M? = null

    override val value: M
        get() {
            return cached ?: block.invoke().apply {
                cached = this
            }
        }

    override fun isInitialized() = cached != null

}