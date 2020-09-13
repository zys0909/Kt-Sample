package com.dev.zhaoys.extend

import android.content.res.Resources
import android.util.TypedValue

/**
 * 描述:
 *
 * author zys
 * create by 2020/8/6
 */
val Number.dp: Int
    get() = when (this) {
        is Int -> (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
        is Float -> (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
        is Double -> (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
        else -> (this.toInt() * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }

val Number.dpf: Float
    get() = when (this) {
        is Int -> this * Resources.getSystem().displayMetrics.density + 0.5f
        is Float -> this * Resources.getSystem().displayMetrics.density + 0.5f
        is Double -> (this * Resources.getSystem().displayMetrics.density + 0.5f).toFloat()
        else -> this.toInt() * Resources.getSystem().displayMetrics.density + 0.5f
    }

fun dp(dp: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)