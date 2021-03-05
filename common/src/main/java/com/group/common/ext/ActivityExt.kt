package com.group.common.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * 描述:
 *
 * author zys
 * create by 2020/7/27
 */

inline fun <reified T> AppCompatActivity.openActivity(bundle: Bundle = bundleOf()) {
    this.startActivity(Intent(this, T::class.java).putExtras(bundle))
//    this.overridePendingTransition(0, 0)
}

inline fun <reified T> Fragment.openActivity(bundle: Bundle = bundleOf()) {

    this.startActivity(Intent(requireContext(), T::class.java).putExtras(bundle))
//    requireActivity().overridePendingTransition(0, 0)
}

fun Context.getActivity(): FragmentActivity? {
    var context: Context = this
    while (context is ContextWrapper) {
        if (context is FragmentActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

val Context.activity: AppCompatActivity?
    get() {
        var context: Context = this
        while (context is ContextWrapper) {
            if (context is AppCompatActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

val View.activity: AppCompatActivity?
    get() = this.context.activity

fun View.fitSystemBar() {
    post {
        val activity = this.context as? Activity
        activity?.fitSystemBar()
        setPadding(0, activity?.statusBarHeight ?: 0, 0, 0)
    }
}

/**
 * StatusBar高度
 */
val Fragment.statusBarHeight: Int
    get() = requireActivity().statusBarHeight

val Activity.statusBarHeight: Int
    get() {
        val result: Int
        val rect = Rect()
        this.window?.decorView?.getWindowVisibleDisplayFrame(rect)
        result = rect.top
        Log.i("测试TAG", "display: $result")
        if (result > 0) {
            return result
        }
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            val offset = resources.getDimensionPixelOffset(resourceId)
            Log.i("测试TAG", "statusBar: $offset")
            offset
        } else 0
    }

/**
 * 透过状态栏
 */
fun Activity.fitSystemBar() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return
    }
    val window = this.window
    val decorView = window.decorView
    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    // 指定状态栏的背景色
    window.statusBarColor = Color.TRANSPARENT
}