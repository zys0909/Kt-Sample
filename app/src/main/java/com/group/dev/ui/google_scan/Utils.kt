package com.group.dev.ui.google_scan

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.TypedValue

/**
 * dp转px
 */
fun Float.toPx(): Int {
    val resources = Resources.getSystem()
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        resources.displayMetrics
    ).toInt()
}


fun isPortraitMode(context: Context): Boolean {
    //获取设置的配置信息
    return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}

