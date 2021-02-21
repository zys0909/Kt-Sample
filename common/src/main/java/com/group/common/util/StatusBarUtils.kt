package com.group.common.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout

import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.graphics.ColorUtils


import com.group.common.R

/**
 * 沉浸式状态栏工具
 *
 * @author JimmySun
 * @since 2019-08-23
 */
@Suppress("unused")
object StatusBarUtils {
    @JvmStatic
    private val FAKE_STATUS_BAR_VIEW_ID = R.id.fake_status_bar_view

    /**
     * 获得状态栏的高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    @JvmStatic
    fun getHeight(context: Context): Int {
        var statusBarHeight = 0
        try {
            val resourceId = context.resources.getIdentifier(
                "status_bar_height", "dimen",
                "android"
            )
            if (resourceId > 0) {
                statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusBarHeight
    }

    /**
     * 设置状态栏颜色
     *
     * @param context 上下文，尽量使用Activity
     * @param color   状态栏颜色
     */
    @JvmStatic
    fun setColor(context: Context, @ColorInt color: Int) {
        if (context is Activity) {
            setColor(context.window, color)
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param window 窗口，可用于Activity和Dialog等
     * @param color  状态栏颜色
     */
    @SuppressLint("ObsoleteSdkInt")
    @JvmStatic
    fun setColor(window: Window, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = color
            setTextDark(window, !isDarkColor(color))
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setColor(window, ColorUtils.blendARGB(Color.TRANSPARENT, color, 0.5f), false)
        }
    }

    /**
     * Android 5.0 以下版本设置状态栏颜色
     *
     * @param window        窗口
     * @param color         状态栏颜色值
     * @param isTransparent 是否透明
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @JvmStatic
    fun setColor(window: Window, @ColorInt color: Int, isTransparent: Boolean) {
        val context = window.context
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        val decorView = window.decorView as ViewGroup
        val contentView = decorView.findViewById<View>(android.R.id.content)
        contentView?.setPadding(0, if (isTransparent) 0 else getHeight(context), 0, 0)
        val fakeStatusBarView = decorView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            fakeStatusBarView.setBackgroundColor(color)
            if (fakeStatusBarView.visibility == View.GONE) {
                fakeStatusBarView.visibility = View.VISIBLE
            }
        } else {
            // 绘制一个和状态栏一样高的矩形
            val statusBarView = View(context)
            val layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getHeight(context)
            )
            statusBarView.layoutParams = layoutParams
            statusBarView.setBackgroundColor(color)
            statusBarView.id = FAKE_STATUS_BAR_VIEW_ID
            decorView.addView(statusBarView)
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param context 上下文，尽量使用Activity
     */
    @JvmStatic
    fun setTransparent(context: Context) {
        if (context is Activity) {
            setTransparent(context.window)
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param window 窗口，可用于Activity和Dialog等
     */
    @SuppressLint("ObsoleteSdkInt")
    @JvmStatic
    fun setTransparent(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setColor(window, 0x80000000.toInt(), true)
        }
    }

    /**
     * 设置状态栏是否为黑色文字
     *
     * @param context 上下文，尽量使用Activity
     * @param isDark  是否为黑色文字
     */
    @JvmStatic
    fun setTextDark(context: Context, isDark: Boolean) {
        if (context is Activity) {
            setTextDark(context.window, isDark)
        }
    }

    /**
     * 设置状态栏是否为黑色文字
     *
     * @param window 窗口，可用于Activity和全屏Dialog
     * @param isDark 是否为黑色文字
     */
    @JvmStatic
    fun setTextDark(window: Window, isDark: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            val systemUiVisibility = decorView.systemUiVisibility
            if (isDark) {
                decorView.systemUiVisibility =
                    systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility =
                    systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            when (OSUtils.getRomType()) {
                OSUtils.ROM.EMUI -> setMIUIDark(window, isDark)
                OSUtils.ROM.Flyme -> setFlymeDark(window, isDark)
                else -> {
                }
            }
        }
    }

    /**
     * 设置MIUI系统状态栏是否为黑色文字
     *
     * @param window 窗口，仅可用于Activity
     * @param isDark 是否为黑色文字
     */
    @SuppressLint("PrivateApi")
    private fun setMIUIDark(window: Window, isDark: Boolean) {
        try {
            val clazz = window.javaClass

            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            val darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.java, Int::class.java)
            extraFlagField.invoke(window, if (isDark) darkModeFlag else 0, darkModeFlag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置Flyme系统状态栏是否为黑色文字
     *
     * @param window 窗口
     * @param isDark 是否为黑色文字
     */
    @JvmStatic
    private fun setFlymeDark(window: Window?, isDark: Boolean) {
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag =
                    WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags =
                    WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                if (isDark) {
                    value = value or bit
                } else {
                    value = value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 判断颜色是否为深色
     *
     * @param color 要判断的颜色
     * @return 是否为深色
     */
    @JvmStatic
    fun isDarkColor(@ColorInt color: Int): Boolean {
//        double darkness =
//                1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
//        return darkness >= 0.5;
        return ColorUtils.calculateLuminance(color) < 0.5
    }
}