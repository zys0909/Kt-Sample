package com.ktx.test

import com.ktx.test.json.*

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val adapter = moShiDefault().adapter(User::class.java)
        val fromJson = adapter.fromJson(json3)
        println(fromJson)
    }
}