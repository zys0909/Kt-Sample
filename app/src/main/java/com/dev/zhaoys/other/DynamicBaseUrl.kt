package com.dev.zhaoys.other

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import java.io.IOException


/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/7/25 0025
 */
object DynamicBaseUrl {
    interface Pop {
        @GET("robots.txt")
        fun robots(): Call<ResponseBody>
    }

    internal class HostSelectionInterceptor : Interceptor {
        @Volatile
        private var host: String? = null

        fun setHost(host: String?) {
            this.host = host
        }

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
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

    @Throws(IOException::class)
    @JvmStatic
    fun test() {
        val hostSelectionInterceptor = HostSelectionInterceptor()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(hostSelectionInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.github.com/")
            .callFactory(okHttpClient)
            .build()

        val pop = retrofit.create(DynamicBaseUrl.Pop::class.java)

        val response1 = pop.robots().execute()
        Log.d("测试TAG", "Response1 from: " + response1.raw().request.url)

        hostSelectionInterceptor.setHost("www.pepsi.com")

        val response2 = pop.robots().execute()
        Log.d("测试TAG", "Response2 from: " + response2.raw().request.url)

        hostSelectionInterceptor.setHost(null)
        val response3 = pop.robots().execute()
        Log.d("测试TAG", "Response3 from: " + response3.raw().request.url)

        val retrofit1 = retrofit.newBuilder().baseUrl("http://www.pepsi.com/").build()
        val pop1 = retrofit1.create(DynamicBaseUrl.Pop::class.java)
        val response4 = pop1.robots().execute()
        Log.d("测试TAG", "Response4 from: " + response4.raw().request.url)
    }
}