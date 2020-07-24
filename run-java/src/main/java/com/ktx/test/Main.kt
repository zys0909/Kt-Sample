package com.ktx.test

import com.ktx.test.api.ApiService
import com.ktx.test.api.TestApi
import com.ktx.test.json.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
//        val adapter = moShiDefault().adapter(User::class.java)
//        val fromJson = adapter.fromJson(json3)
//        println(fromJson)
//        val format = SimpleDateFormat("yyyy", Locale.CHINA).format(0)
//        val result = moShiCreate().adapter(TrackJson::class.java).fromJson(trackJson)
//        println(result)
//        val push = ApiService.testApi.push(TestApi.auth_string)
//        println(push.toString())
//        print(TestApi.auth_string)
        val num = 2000897789876866.00060
        val format = NumberFormat.getInstance().apply {
            maximumFractionDigits = 2
            roundingMode = RoundingMode.FLOOR
            isGroupingUsed = false
        }.format(num)

        println("原数 $num")
        println(format)
        println(BigDecimal(num).setScale(2, RoundingMode.HALF_UP).toString())

    }
}