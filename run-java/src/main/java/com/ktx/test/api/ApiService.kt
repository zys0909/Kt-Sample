package com.ktx.test.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 描述:
 *
 * author zys
 * create by 2020/7/24
 */
class ApiService private constructor() {
    companion object {
        private val retrofit: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            val clientBuild =
                OkHttpClient.Builder()
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                    .readTimeout(10000, TimeUnit.MILLISECONDS)

            clientBuild.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

            Retrofit.Builder()
                .baseUrl("http://centanet.com.cn/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(clientBuild.build())
                .build()
        }

        val testApi: TestApi by lazy {
            retrofit.create(TestApi::class.java)
        }
    }

}