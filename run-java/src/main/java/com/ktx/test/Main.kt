package com.ktx.test

import com.ktx.test.json.*
import com.ktx.test.jsonadapter.NullJsonFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat

object Main {
    var flag = 0

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

        val build = Moshi.Builder()
            .add(NullJsonFactory.STANDARD)
            .add(NullJsonFactory.COLLECTION)
            .build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)

        flag = 5
        when (flag) {
            2 -> numTest()
            3 -> {
                val jsonAdapter = build.adapter(Test::class.java)
                val fromJson = jsonAdapter.fromJson(test3)
                print(fromJson)
            }
            4 -> {
                val adapter = build.adapter(UserTest::class.java)
                val toJson = adapter.toJson(UserTest(name = "abc",userId = ""))
                println("main -> $toJson")
            }
            5->{
                val jsonAdapter = build.adapter(UserTest::class.java)
                val fromJson = jsonAdapter.fromJson(json3)
                print(fromJson)
            }
        }



    }

    private fun numTest() {
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