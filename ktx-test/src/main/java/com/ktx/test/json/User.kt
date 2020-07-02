package com.ktx.test.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


val json = "{\n" +
        "\t\"name\": \"zhaoys\",\n" +
        "\t\"userId\": null,\n" +
        "\t\"h\": 17.5\n" +
        "}"

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "h")
    val h: Double,
    @Json(name = "name")
    val name: String,
    @Json(name = "userId")
    val userId: String
)