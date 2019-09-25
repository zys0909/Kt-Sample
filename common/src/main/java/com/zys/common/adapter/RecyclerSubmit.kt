package com.zys.common.adapter

data class RecyclerSubmit(
    val pageIndex: Int = 1,
    val pageCount: Int = 10,
    val total: Int = 0
)