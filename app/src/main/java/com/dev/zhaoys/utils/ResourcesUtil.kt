package com.dev.zhaoys.utils

import androidx.annotation.DimenRes
import com.dev.zhaoys.app.App

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/7/29 0029
 */
object ResourcesUtil {

    @JvmStatic
    fun getDimensionPixelOffset( @DimenRes id: Int): Int =
        App.instance.resources.getDimensionPixelOffset(id)
}