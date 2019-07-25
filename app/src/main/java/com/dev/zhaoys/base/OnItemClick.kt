package com.dev.zhaoys.base

import android.view.View

/**
 * 描述:Item点击回调
 *
 * author zhaoys
 * create by 2019/7/23 0023
 */
interface OnItemClick {
    fun itemClick(view: View, position: Int)
}