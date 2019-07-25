package com.dev.zhaoys.data.response

import com.squareup.moshi.JsonClass

/**
 * 描述:
 *
 * @author zhaoys
 * create by 2019/7/1 0001 13:52
 */
@JsonClass(generateAdapter = true)
data class ArticleListData(
    val curPage: Int,
    val datas: List<ArticleData>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Long
)