package com.ktx.test.jsonadapter

import com.squareup.moshi.JsonReader

/**
 * 描述:对null数据及不匹配的类型兼容处理
 *
 * author zys
 * create by 2020/7/30
 */
internal fun JsonReader.next(token: JsonReader.Token): Boolean {
    val peek = peek()
    return when {
        peek == token -> false
        peek == JsonReader.Token.NULL -> {
            nextNull<Any>()
            true
        }
        NullJsonFactory.allowServiceError -> when (peek) {
            JsonReader.Token.BEGIN_ARRAY -> {
                beginArray()
                endArray()
                true
            }
            JsonReader.Token.BOOLEAN -> {
                nextBoolean()
                true
            }
            JsonReader.Token.STRING -> {
                nextString()
                true
            }
            JsonReader.Token.NUMBER -> {
                nextDouble()
                true
            }
            else -> false
        }
        else -> false
    }
}