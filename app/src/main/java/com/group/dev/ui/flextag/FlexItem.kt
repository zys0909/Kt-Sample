package com.group.dev.ui.flextag

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.group.common.ext.dp
import com.group.common.ext.dpf

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/3
 */
sealed class FlexItem

class FlexTextItem(val text: String) : FlexItem()

/**
 * 图标
 */
class FlexIconItem(@DrawableRes val iconRes: Int) : FlexItem()

/**
 * @param text 标签文字
 * @param textSize 文字大小 ，若不设置，则使用flexTag_textSize
 * @param textColor 文字颜色，若不设置，则使用flexTag_textColor
 * @param drawable 背景色
 * @param padding 内边距
 */
class FlexSpecialItem(
    val text: String,
    val textSize: Float = 10.dpf,
    @ColorInt
    val textColor: Int = Color.BLACK,
    val drawable: Drawable? = null,
    val padding: Rect = Rect(3.dp, 0, 3.dp, 0)
) : FlexItem() {
    constructor(text: String, textSize: Float, textColor: Int, bgColor: Int) : this(
        text, textSize, textColor,
        ViewUtil.createDrawable(bgColor, 1.dpf, Color.TRANSPARENT, 0)
    )
}