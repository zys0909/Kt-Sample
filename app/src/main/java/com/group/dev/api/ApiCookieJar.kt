package com.group.dev.api

import com.tencent.mmkv.MMKV
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * 描述:
 *
 * author zys
 * create by 2021/4/17
 */
class ApiCookieJar :CookieJar {
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        MMKV.defaultMMKV()?.decodeBytes("")
        return emptyList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        MMKV.defaultMMKV()?.encode("cookie","")
    }
}