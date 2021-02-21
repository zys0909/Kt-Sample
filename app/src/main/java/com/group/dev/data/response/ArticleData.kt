package com.group.dev.data.response

import com.squareup.moshi.JsonClass

/**
 * 描述:文章数据实体
 *
 * author zhaoys
 * create by 2019/7/22 0022
 */
@JsonClass(generateAdapter = true)
data class ArticleData(
    /**
    {
    "apkLink": "",
    "author": "鸿洋",
    "chapterId": 361,
    "chapterName": "课程推荐",
    "collect": false,
    "courseId": 13,
    "desc": "",
    "envelopePic": "",
    "fresh": false,
    "id": 8537,
    "link": "https://market.geekbang.org/activity/channelcoupon/15?utm_source=web&amp;utm_medium=wananzhuo&amp;utm_campaign=changweiliuliang&amp;utm_term=zhanghongyang003&amp;utm_content=0530",
    "niceDate": "2019-07-14",
    "origin": "",
    "prefix": "",
    "projectLink": "",
    "publishTime": 1563111162000,
    "superChapterId": 249,
    "superChapterName": "干货资源",
    "tags": [],
    "title": "跟极客时间申请了一波199优惠券免费送 每人仅能领取一次",
    "type": 1,
    "userId": -1,
    "visible": 1,
    "zan": 0
    }
     */
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    var collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Any>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)