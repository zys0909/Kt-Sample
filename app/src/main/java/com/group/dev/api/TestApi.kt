package com.group.dev.api

import com.group.dev.data.response.ArticleData
import com.group.dev.data.response.ArticleListData
import com.group.dev.data.response.HomeBannerData
import com.group.dev.data.response.Response
import okhttp3.ResponseBody
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
     * 首页置顶文章
     */
    @GET("article/top/json")
    suspend fun testTop(): ResponseBody

    /**
     * 首页banner
     */
    @GET("banner/json")
    suspend fun homeBanner(): Response<List<HomeBannerData>>

}