package com.dev.zhaoys.utils

import org.json.JSONArray

/**
 * 描述:
 *
 * author zys
 * create by 2019-12-25
 */
object StrUtil {
    /**
     * 判断字符串是否可以转化为JSON数组
     * @param content
     * @return
     */
    @JvmStatic
    fun isJsonArray(content: String): Boolean {
        if (content.isBlank())
            return false
        return try {
            JSONArray(content)
            true
        } catch (e: Exception) {
            false
        }

    }
}