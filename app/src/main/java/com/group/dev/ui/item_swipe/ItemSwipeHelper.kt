package com.group.dev.ui.item_swipe

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

/**
 * 描述: RecyclerView 侧滑删除
 *
 * 若某个ViewHolder不需要侧滑，让该ViewHolder实现 [ItemSwipe] 接口，[ItemSwipe.enable]返回false
 * author zys
 * create by 2021/3/11
 */

object ItemSwipeHelper {

    @JvmStatic
    fun attach(activity: FragmentActivity, recyclerView: RecyclerView) {
        ItemSwipeHelperImpl(activity.lifecycle).attachToRecyclerView(recyclerView)
    }

    @JvmStatic
    fun attach(fragment: Fragment, recyclerView: RecyclerView) {
        ItemSwipeHelperImpl(fragment.lifecycle).attachToRecyclerView(recyclerView)
    }
}



