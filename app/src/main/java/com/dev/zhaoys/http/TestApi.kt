package com.dev.zhaoys.http

import com.dev.zhaoys.data.response.ArticleData
import com.dev.zhaoys.data.response.ArticleListData
import com.dev.zhaoys.data.response.HomeBannerData
import com.dev.zhaoys.data.response.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 描述:
 *
 * @author zhaoys
 * create by 2019/7/1 0001 13:25
 */
interface TestApi {
    /**
     * 文章列表
     */
    @GET("article/list/{id}/json")
    suspend fun articleList(@Path("id") id: Int): Response<ArticleListData>

    /**
     * 首页置顶文章
     */
    @GET("article/top/json")
    suspend fun articleTop(): Response<List<ArticleData>>

    /**
     * 首页banner
     */
    @GET("banner/json")
    suspend fun homeBanner(): Response<List<HomeBannerData>>

}