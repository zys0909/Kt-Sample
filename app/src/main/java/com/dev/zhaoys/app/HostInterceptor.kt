package com.dev.zhaoys.app

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 描述:Host拦截器
 *
 * author zhaoys
 * create by 2019/7/25 0025
 */
class HostInterceptor : Interceptor {
    @Volatile
    private var host: String? = null

    fun setHost(host: String?) {
        this.host = host
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val host = this.host
        if (host != null) {
            val newUrl = request.url.newBuilder()
                .host(host)
                .build()
            request = request.newBuilder()
                .url(newUrl)
                .build()
        }
        return chain.proceed(request)
    }
}