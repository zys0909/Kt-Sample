package com.group.dev.ext

import android.content.res.Resources

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/19
 */


object DensityUtil {

    @JvmStatic
    fun dp2px(dpValue: Number): Int = dpValue.dp

}

val Number.dp: Int
    get() = this.dpf.toInt()

val Number.dpf: Float
    get() = when (this) {
        is Int -> this * Resources.getSystem().displayMetrics.density + 0.5f
        is Float -> this * Resources.getSystem().displayMetrics.density + 0.5f
        is Double -> (this * Resources.getSystem().displayMetrics.density + 0.5f).toFloat()
        else -> this.toInt() * Resources.getSystem().displayMetrics.density + 0.5f
    }
