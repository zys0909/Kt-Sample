@file:Suppress("UNCHECKED_CAST","UNUSED")

package com.group.dev

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.reflect.Field
import java.lang.reflect.Method


/**
 * LiveDataBus
 * 基于LiveData实现的事件总线
 */
object LiveDataBus {
    private val mBus by lazy { mutableMapOf<String, BusLiveData<*>>() }

    fun <T : Any> observe(channel: Class<T>, owner: LifecycleOwner, observer: Observer<T>) {
        getChannel(channel).observe(owner, observer)
    }

    fun <T : Any> observeSticky(channel: Class<T>, owner: LifecycleOwner, observer: Observer<T>) {
        getChannel(channel).observeStick(owner, observer)
    }

    fun <T : Any> observeForever(channel: Class<T>, observer: Observer<T>) {
        getChannel(channel).observeForever(observer)
    }

    fun <T : Any> observeStickyForever(channel: Class<T>, observer: Observer<T>) {
        getChannel(channel).observeStickyForever(observer)
    }

    fun <T : Any> removeObserver(channelClass: Class<T>, observer: Observer<T>) {
        getChannel(channelClass).removeObserver(observer)
    }

    fun <T : Any> postMessage(message: T) {
        getChannel(message::class.java as Class<T>).postValue(message)
    }

    private fun <T : Any> getChannel(channelClass: Class<T>): BusLiveData<T> {
        return mBus.getOrPut(channelClass.simpleName) {
            BusLiveData<T>()
        } as BusLiveData<T>
    }

    /**
     * 自定义的LiveData
     *
     * 用于通过`observe()`加入的`observer`
     * 通过反射，使`ObserverWrapper`维护的数据版本等于当前`LiveData`数据版本。
     * 在触发生命周期回调时，由于数据版本相同，系统判断为已通知，即不会触发数据更新。
     *
     * 使通过`observeForever()`新加入的`observer`
     * 需要屏蔽所有通过`observeForever()`发起的`onChanged()`调用。
     * 通过把传入的`observer`通过一个自定义的`ObserverWrapper`装修类。
     * 在`onChanged()`判断当前调用栈是否有`observeForever()`，存在时则不触发实际的`observer.onChanged()`。
     */
    private class BusLiveData<T> : MutableLiveData<T>() {

        // 反射缓存
        private companion object {
            private const val fieldObservers = "mObservers"
            private const val methodGet = "get"
            private const val fieldLastVersion = "mLastVersion"
            private const val fieldVersion = "mVersion"
            private val mCache = hashMapOf<String, Any>()
        }

        private val mRealMap = hashMapOf<Observer<*>, Observer<*>>()

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            setObserverVerToLiveDataVer(observer)
        }

        fun observeStick(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
        }

        override fun observeForever(observer: Observer<in T>) {
            super.observeForever(mRealMap.getOrPut(observer) {
                WrapperObserver(observer)
            } as Observer<in T>)
        }

        fun observeStickyForever(observer: Observer<in T>) {
            super.observeForever(observer)
        }

        override fun removeObserver(observer: Observer<in T>) {
            if (mRealMap.containsKey(observer)) {
                super.removeObserver(mRealMap.remove(observer) as Observer<T>)
            } else {
                super.removeObserver(observer)
            }
        }

        private fun setObserverVerToLiveDataVer(observer: Observer<in T>) {
            val liveDataClass = LiveData::class.java
            try {
                val mObserversField: Field = mCache.getOrPut(fieldObservers) {
                    val field = liveDataClass.getDeclaredField(fieldObservers)
                    field.isAccessible = true
                    field
                } as Field
                val mObservers = mObserversField[this]

                val getMethod = mCache.getOrPut(methodGet) {
                    val method = mObservers.javaClass.getDeclaredMethod(methodGet, Any::class.java)
                    method.isAccessible = true
                    method
                } as Method

                val boundObserverEntry = getMethod.invoke(mObservers, observer)
                var boundObserver: Any? = null
                if (boundObserverEntry is Map.Entry<*, *>) {
                    boundObserver = boundObserverEntry.value
                }
                if (boundObserver == null) {
                    throw NullPointerException("LifecycleBoundObserver cant be null")
                }

                val mLastVersionField = mCache.getOrPut(fieldLastVersion) {
                    val wrapperClass: Class<in Any> = boundObserver.javaClass.superclass
                            ?: throw NullPointerException("Cant access ObserverWrapper.class")
                    val field = wrapperClass.getDeclaredField(fieldLastVersion)
                    field.isAccessible = true
                    field
                } as Field

                val mVersionField = mCache.getOrPut(fieldVersion) {
                    val field = liveDataClass.getDeclaredField(fieldVersion)
                    field.isAccessible = true
                    field
                } as Field

                mLastVersionField.set(boundObserver, mVersionField[this])
            } catch (e: Exception) {
            }
        }
    }

    /**
     * Observer包装类
     */
    private class WrapperObserver<T>(
            private val observer: Observer<T>
    ) : Observer<T> {

        private companion object {
            private const val clazz = "android.arch.lifecycle.LiveData"
            private const val func = "observeForever"
        }

        override fun onChanged(t: T) {
            if (!isCallOnObserve()) {
                observer.onChanged(t)
            }
        }

        private fun isCallOnObserve(): Boolean {
            for (element in Thread.currentThread().stackTrace) {
                if (clazz == element.className && func == element.methodName) {
                    return true
                }
            }
            return false
        }
    }
}