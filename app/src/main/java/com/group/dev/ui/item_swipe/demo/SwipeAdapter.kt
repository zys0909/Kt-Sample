package com.group.dev.ui.item_swipe.demo

import com.group.common.adapter.RecyclerAdapter
import com.group.common.adapter.RecyclerSupport

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
class SwipeAdapter(val support: RecyclerSupport) : RecyclerAdapter(support) {


    fun removeAt(position: Int) {
        val temp = MutableList(currentList.size) {
            currentList[it]
        }
        temp.removeAt(position)
        submit(temp)
    }
}

