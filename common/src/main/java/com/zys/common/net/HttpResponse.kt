package com.zys.common.net

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/14
 */
data class HttpResponse<T>(
    val ResultNo: Int,
    val Total: Int = 0,
    val Result: T? = null,
)