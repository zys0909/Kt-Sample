package com.group.common.net

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/14
 */
class RequestCoroutineScopeActivityImpl(
    private val lifecycle: Lifecycle,
    override val coroutineContext: CoroutineContext
) : RequestCoroutineScope(), LifecycleEventObserver {


    init {
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            coroutineContext.cancel()
        }
    }

    override fun <T> launchDefault(realNetWork: suspend () -> HttpResponse<T>): LiveData<HttpResponse<T>> {
        return RealNetWork(realNetWork).execute(this)
    }

    override suspend fun <T> launchSync(realNetWork: suspend () -> HttpResponse<T>): HttpResponse<T> {
        return RealNetWork(realNetWork).syncExecute(this)
    }


    fun register() {
        launch(Dispatchers.Main.immediate) {
            if (lifecycle.currentState >= Lifecycle.State.INITIALIZED) {
                lifecycle.addObserver(this@RequestCoroutineScopeActivityImpl)
            } else {
                coroutineContext.cancel()
            }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (lifecycle.currentState <= Lifecycle.State.DESTROYED) {
            lifecycle.removeObserver(this)
            coroutineContext.cancel()
        }

    }
}