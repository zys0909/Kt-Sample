package com.group.common.net

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/14
 */
class RealNetWork<T>(private val requestBlock: suspend () -> HttpResponse<T>) {


    private val liveData: MutableLiveData<HttpResponse<T>> by lazy {
        MutableLiveData()
    }

    fun execute(scope: CoroutineScope): LiveData<HttpResponse<T>> {
        scope.launch(Dispatchers.IO) {
            try {
                val response = requestBlock.invoke()
                liveData.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(HttpResponse(ERROR_NET))
            }
        }
        return liveData
    }

    suspend fun syncExecute(scope: CoroutineScope): HttpResponse<T> {
        return try {
            requestBlock.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
            HttpResponse(ERROR_NET)
        }
    }
}