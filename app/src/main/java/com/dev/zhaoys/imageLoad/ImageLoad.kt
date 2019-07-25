package com.dev.zhaoys.imageLoad

import android.widget.ImageView
import com.dev.zhaoys.R

/**
 * ImageLoad
 *
 */
interface ImageLoad<E> {

    /**
     * @param circleCrop 是否加载圆形图片
     * @param crossFade 是否使用加载过度动画
     */
    fun load(
        view: ImageView,
        source: E?,
        placeholder: Int = R.drawable.ic_placeholder,
        error: Int = R.drawable.ic_placeholder,
        fallback: Int = R.drawable.ic_placeholder,
        circleCrop: Boolean = false,
        crossFade: Boolean = true
    )
}