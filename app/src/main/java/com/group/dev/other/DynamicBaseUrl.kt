package com.group.dev.other

import android.util.Log
import okhttp3.*
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
                    .url(host)
                    .build()
            }
            return chain.proceed(request)
        }
    }


    class CallFactoryProxy(private val httpClient: OkHttpClient) : okhttp3.Call.Factory {

        @Volatile
        private var baseUrl: String? = null

        fun url(baseUrl: String?) {
            this.baseUrl = baseUrl
        }

        override fun newCall(request: Request): okhttp3.Call {
            val req: Request = if (baseUrl != null)
                request.newBuilder().url(baseUrl!!).build()
            else
                request
            return httpClient.newCall(req)
        }
    }


    @Throws(IOException::class)
    @JvmStatic
    fun test() {
        val hostSelectionInterceptor = HostSelectionInterceptor()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(hostSelectionInterceptor)
            .build()

        val porxy = CallFactoryProxy(okHttpClient)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.github.com/")
            .callFactory(porxy)
            .build()

        val pop = retrofit.create(Pop::class.java)

        val response1 = pop.robots().execute()
        Log.d("测试TAG", "Response1 from: " + response1.raw().request.url)

       // hostSelectionInterceptor.setHost("http://www.github.com/")
        porxy.url("http://www.github.com/")
        val response2 = pop.robots().execute()
        Log.d("测试TAG", "Response2 from: " + response2.raw().request.url)

        porxy.url(null)
        val response3 = pop.robots().execute()
        Log.d("测试TAG", "Response3 from: " + response3.raw().request.url)
    }
}