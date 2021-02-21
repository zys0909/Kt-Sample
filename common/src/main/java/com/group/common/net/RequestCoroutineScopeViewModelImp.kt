package com.group.common.net

import androidx.lifecycle.LiveData
import kotlinx.coroutines.cancel
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/14
 */
class RequestCoroutineScopeViewModelImp(context: CoroutineContext,

) : RequestCoroutineScope(),
    Closeable {
    override val coroutineContext: CoroutineContext = context


    override  fun <T> launchDefault(realNetWork: suspend () -> HttpResponse<T>): LiveData<HttpResponse<T>> {
        return RealNetWork(realNetWork).execute(this)
    }

    override suspend fun <T> launchSync(realNetWork: suspend () -> HttpResponse<T>): HttpResponse<T> {
        return RealNetWork(realNetWork).syncExecute(this)
    }

    override fun close() {
        coroutineContext.cancel()
    }

}