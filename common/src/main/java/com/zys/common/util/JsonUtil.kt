@file:Suppress("unused", "SpellCheckingInspection")

package com.zys.common.util

import android.os.Bundle
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.json.JSONArray
import org.json.JSONObject

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/20
 */
object JsonUtil {

    /**
     */
    val moShi: Moshi
        get() = Moshi.Builder()
            .build()

    fun throwException(error: String?) {
        Log.e("JsonUtil", error ?: "")
    }

    /**
     * 解析json数组
     */
    inline fun <reified E> parseArray(json: String?): List<E> {
        val result = mutableListOf<E>()
        if (json.isNullOrEmpty()) return result
        try {
            val type = Types.newParameterizedType(List::class.java, E::class.java)
            val jsonAdapter: JsonAdapter<List<E>> = moShi.adapter(type)
            jsonAdapter.fromJson(json)?.run {
                result.addAll(this)
            }
        } catch (e: Exception) {
            throwException(e.message)
        }
        return result
    }

    /**
     * 解析json数组
     */
    fun <E> parseArray(json: String?, clazz: Class<E>): List<E> {
        val result = mutableListOf<E>()
        if (json.isNullOrEmpty()) return result
        try {
            val type = Types.newParameterizedType(List::class.java, clazz)
            val jsonAdapter: JsonAdapter<List<E>> = moShi.adapter(type)
            jsonAdapter.fromJson(json)?.run {
                result.addAll(this)
            }
        } catch (e: Exception) {
            throwException(e.message)
        }
        return result
    }

    /**
     * 解析json对象
     */
    inline fun <reified E> parseObject(json: String?): E? {
        if (json.isNullOrEmpty()) return null
        try {
            val jsonAdapter = moShi.adapter(E::class.java)
            return jsonAdapter.fromJson(json)
        } catch (e: Exception) {
            throwException(e.message)
        }
        return null
    }

    /**
     *解析jsonMap
     */
    inline fun <reified V> parseMap(json: String?): MutableMap<String, V?> {
        val map = mutableMapOf<String, V?>()
        if (json.isNullOrEmpty()) return map
        try {
            val type = Types.newParameterizedType(
                MutableMap::class.java,
                String::class.java,
                V::class.java
            )
            val jsonAdapter: JsonAdapter<MutableMap<String, V>> = moShi.adapter(type)
            jsonAdapter.fromJson(json)?.run {
                map.putAll(this)
            }
        } catch (e: Exception) {
            throwException(e.message)
        }
        return map
    }

    /**
     * 对象 转 字符串
     */
    inline fun <reified T> toJson(t: T?): String {
        return try {
            moShi.adapter(T::class.java).toJson(t)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }


    /**
     * 对象 转 字符串
     */
    fun <T> toJson1(t: T?): String {
        if (t == null) {
            return ""
        }
        return try {
            moShi.adapter(t.javaClass).toJson(t)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }


    /**
     * 数组 转 字符串
     */
    inline fun <reified T> toJson(list: List<T>): String {
        return try {
            val type = Types.newParameterizedType(List::class.java, T::class.java)
            val jsonAdapter: JsonAdapter<List<T>> = moShi.adapter(type)
            jsonAdapter.toJson(list)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * Bundle 转 字符串
     */
    fun toJson(bundle: Bundle?): String {
        val map = mutableMapOf<String, Any?>()
        if (bundle != null) {
            try {
                for (key in bundle.keySet()) {
                    map[key] = bundle.get(key)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return toJson(map)
    }

    /**
     *判断字符串 是否是json格式
     */
    fun isJson(s: String?): Boolean {
        if (s.isNullOrEmpty()) return false
        return try {
            JSONObject(s)
            true
        } catch (e: Exception) {
            isJsonArray(s)
        }
    }

    /**
     *判断字符串 是否是json数组格式
     */
    @JvmStatic
    fun isJsonArray(s: String?): Boolean {
        return try {
            JSONArray(s)
            true
        } catch (e: Exception) {
            false
        }
    }
}