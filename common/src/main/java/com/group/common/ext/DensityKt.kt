package com.group.common.ext

import android.content.res.Resources
import android.util.TypedValue

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
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )
