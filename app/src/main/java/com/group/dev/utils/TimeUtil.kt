package com.group.dev.utils

/**
 * 描述:时间工具
 *
 * author zhaoys
 * create by 2019/7/23 0023
 */
object TimeUtil {

    /**
     * 是否在24小时内
     */
    fun isToday(longTime: Long): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        return longTime > currentTimeMillis - 24 * 60 * 60 * 1000
    }
}