package com.dev.zhaoys.data.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeBannerData(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)