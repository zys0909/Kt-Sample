package com.ktx.test.json

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


/**
 * 描述:
 *
 * author zys
 * create by 2020/7/24
 */
@JsonClass(generateAdapter = true)
data class PushResult(
    @Json(name = "msg_id")
    val msgId: String = "", // 29273477786775623
    @Json(name = "sendno")
    val sendNo: String = "" // 0
)