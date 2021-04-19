package com.group.dev.api

import okhttp3.*

/**
 * 描述:
 *
 * author zys
 * create by 2021/4/17
 */
class ApiAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        val basic = Credentials.basic("username", "password")
        return response.request.newBuilder().addHeader("Proxy-Authorization", basic).build()
    }
}