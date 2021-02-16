package com.group.dev.api

import android.util.Log
import com.dev.zhaoys.app.HostInterceptor
import com.group.dev.constant.AppConst
import com.zys.core.App
import okhttp3.Cache
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 描述:
 *
 * @author zys
 * create by 2019/7/1 0001 11:27
 */
object ApiService {

    private val retrofit: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val clientBuild = OkHttpClient.Builder()
            .connectTimeout(AppConst.HTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(AppConst.HTTP_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(AppConst.HTTP_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(AppConst.HTTP_TIMEOUT, TimeUnit.SECONDS)
            .cache(Cache(App.instance.cacheDir, AppConst.HTTP_Cache))
            .dns(Dns.SYSTEM)
            .addInterceptor(HostInterceptor())

        if (App.debug) {
            val httpLog = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.i("OKHttp-----", message)
                }
            })
            httpLog.level = HttpLoggingInterceptor.Level.BODY
            clientBuild.addInterceptor(httpLog)
        }

        Retrofit.Builder()
            .baseUrl(AppConst.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(clientBuild.build())
            .build()
    }

    @get:JvmName("testApi")
    val testApi: TestApi by lazy {
        retrofit.create(TestApi::class.java)
    }
}