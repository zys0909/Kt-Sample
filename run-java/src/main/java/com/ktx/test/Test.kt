package com.ktx.test

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

val test1 = "{\n" +
        "\t\"list\": [null, \"andhh\", \"wee\"]\n" +
        "}"

val test2 = "{\n" +
        "\t\"list\": \"add\"\n" +
        "}"
val test3 = "{\n" +
        "\t\"list\": [\"add\", 3]\n" +
        "}"
/**
 * 描述:
 *
 * author zys
 * create by 2020/7/30
 */
@JsonClass(generateAdapter = true)
data class Test(
    val list: List<String> = listOf()
)