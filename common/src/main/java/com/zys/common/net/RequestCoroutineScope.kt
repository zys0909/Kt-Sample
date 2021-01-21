package com.zys.common.net

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/14
 */
abstract class RequestCoroutineScope : CoroutineScope {

    abstract fun <T> launchDefault(realNetWork: suspend () -> HttpResponse<T>): LiveData<HttpResponse<T>>

    abstract suspend fun <T> launchSync(realNetWork: suspend () -> HttpResponse<T>): HttpResponse<T>
}