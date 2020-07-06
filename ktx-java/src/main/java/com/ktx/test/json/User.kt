package com.ktx.test.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


val json = "{\n" +
        "\t\"name\": \"zhaoys \",\n" +
        "\t\"userId \": \"123\",\n" +
        "\t\"h \": 17.5,\n" +
        "\t\"group\": {\n" +
        "\t\t\"name\": \"centa\",\n" +
        "\t\t\"artifact\": \"android\"\n" +
        "\t},\n" +
        "\t\"list\": [1, 2, 3, 4]\n" +
        "}"
val json2 = "{\n" +

        "\t\"userId \": \"123\",\n" +
        "\t\"h \": 17.5,\n" +
        "\t\"group\": {\n" +
        "\t\t\"name\": \"centa\",\n" +
        "\t\t\"artifact\": \"android\"\n" +
        "\t}\n" +
        "}"
val json3 = "{\n" +
        "\t\"name\": \"zhaoys \",\n" +
        "\t\"userId \": \"123\",\n" +
        "\t\"h \": 17.5,\n" +
        "\t\"group\": {\n" +
        "\t\t\"name\": \"centa\",\n" +
        "\t\t\"artifact\": \"android\"\n" +
        "\t}\n" +
        "}"


@JsonClass(generateAdapter = true)
data class User(
    val group: Group = Group(),
    @Json(name = "h ")
    val h: Double = 0.0, // 17.5
    val list: List<Int> = listOf(),
    val name: String = "", // zhaoys
    @Json(name = "userId ")
    val userId: String = "" // 123
)

@JsonClass(generateAdapter = true)
data class Group(
    val artifact: String = "", // android
    val name: String = "" // centa
)