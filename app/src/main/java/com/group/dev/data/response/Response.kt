package com.group.dev.data.response

import com.squareup.moshi.JsonClass

/**
 * 描述:
 *
 * @author zhaoys
 * create by 2019/7/1 0001 17:32
 */
@JsonClass(generateAdapter = true)
data class Response<T>(
    val data: T?,
    val errorCode: Int,
    val errorMsg: String
)