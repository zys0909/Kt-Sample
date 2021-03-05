package com.group.common.util

import android.text.format.DateUtils

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
    fun isToday(longTime: Long?): Boolean {
        return if (longTime == null) false else DateUtils.isToday(longTime)
    }
}