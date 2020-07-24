package com.ktx.test.json

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


/**
 * 描述:
 *
 * author zys
 * create by 2020/7/17
 */
val trackJson = "{\n" +
        "\t\"isStaff400\": true,\n" +
        "\t\"eventId\": \"conversion\",\n" +
        "\t\"vars\": {\n" +
        "\t\t\"bh_channel\": \"新房\",\n" +
        "\t\t\"bh_location\": \"新房详情_楼盘比价_楼盘参数对比_400电话\",\n" +
        "\t\t\"bh_page\": \"楼盘参数对比页\",\n" +
        "\t\t\"cr_type\": \"400来电\",\n" +
        "\t\t\"phone_400\": \"4008192186,901329\"\n" +
        "\t}\n" +
        "}"

@JsonClass(generateAdapter = true)
data class TrackJson(
    val eventId: String = "", // conversion
    val isStaff400: Boolean = false, // true
    val vars: MutableMap<String, Any>?
)

@JsonClass(generateAdapter = true)
data class Vars(
    @Json(name = "bh_channel")
    val bhChannel: String?, // 新房
    @Json(name = "bh_location")
    val bhLocation: String?, // 新房详情_楼盘比价_楼盘参数对比_400电话
    @Json(name = "bh_page")
    val bhPage: String?, // 楼盘参数对比页
    @Json(name = "cr_type")
    val crType: String?, // 400来电
    @Json(name = "phone_400")
    val phone400: String? // 4008192186,901329
)