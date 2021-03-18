package com.group.dev.ui.item_swipe

import androidx.recyclerview.widget.RecyclerView

/**
 * 描述: RecyclerView 侧滑删除
 * @param maxSwipeWidth 最大滑动宽度
 * @param duration 从最大滑动宽度，到恢复初始状态执行动画所需的时间（毫秒）
 *
 * 若某个ViewHolder不需要侧滑，让该ViewHolder实现 [ItemSwipe] 接口，[ItemSwipe.enable]返回false
 * author zys
 * create by 2021/3/11
 */
fun RecyclerView.setSwipeHelper(maxSwipeWidth: Int, duration: Int = 200) {
    ItemSwipeHelper(maxSwipeWidth, duration).attachToRecyclerView(this)
}



