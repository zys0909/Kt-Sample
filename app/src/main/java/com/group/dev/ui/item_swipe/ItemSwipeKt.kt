package com.group.dev.ui.item_swipe

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 描述: RecyclerView 侧滑删除
 *
 * author zys
 * create by 2021/3/12
 */

/**
 * 设置侧滑
 * @param maxSwipeWidth 最大滑动宽度
 */
fun RecyclerView.setSwipeHelper(maxSwipeWidth: Int) {
    ItemSwipeHelper(maxSwipeWidth).attachToRecyclerView(this)
}

/**
 * 点击
 */
fun RecyclerView.setOnItemClickListener(listener: (View, Int) -> Unit) {
    this.addOnItemTouchListener(ItemTouchListener(this, listener))
}


