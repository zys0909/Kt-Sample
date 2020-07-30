package com.dev.zhaoys.app

import com.dev.zhaoys.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 描述:
 *
 * @author zhaoys
 * create by 2019/7/1 0001 11:27
 */
class ApiService private constructor() {

    companion object {
        private val retrofit: Retrofit by lazy {
            val clientBuild = OkHttpClient.Builder()
                .connectTimeout(BuildConfig.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(BuildConfig.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .cache(Cache(App.instance.cacheDir, 104857600L))
                .addInterceptor(HostInterceptor())

            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                clientBuild.addInterceptor(httpLoggingInterceptor)
            }

            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(clientBuild.build())
                .build()
        }

        val testApi: TestApi by lazy {
            retrofit.create(TestApi::class.java)
        }
    }
}