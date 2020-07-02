package com.ktx.test

import com.ktx.test.json.NullAdapters
import com.ktx.test.json.User
import com.ktx.test.json.json
import com.squareup.moshi.Moshi

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val adapter = Moshi.Builder().add(NullAdapters.FACTORY).build().adapter(User::class.java)
        val fromJson = adapter.fromJson(json)
        println(fromJson)
    }
}