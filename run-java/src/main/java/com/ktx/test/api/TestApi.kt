package com.ktx.test.api

import com.ktx.test.json.PushResult
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.*

/**
 * 描述:
 *
 * author zys
 * create by 2020/7/24
 */
interface TestApi {
    companion object {
        private val credentials =
            "a607fc7ce3caa49c2d4bea11:924c6aff2308f30ad2e04025".toByteArray(Charsets.UTF_8)
        val auth_string = "Basic " + Base64.getEncoder().encodeToString(credentials)
    }


    @POST("https://api.jpush.cn/v3/push")
    suspend fun push(@Header("Authorization") author: String = auth_string): PushResult
}