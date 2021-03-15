package com.group.dev.ui.item_swipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.group.common.adapter.RecyclerSupport
import com.group.dev.R

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
class SwipeAdapter(val support: RecyclerSupport) : RecyclerView.Adapter<SwipeCellVH>() {

    val dataList = mutableListOf<SwipeCell>()


    fun submit(list: List<SwipeCell>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeCellVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_date_cell, parent, false)
        return SwipeCellVH(view, support)
    }

    override fun onBindViewHolder(holder: SwipeCellVH, position: Int) {
        holder.bind(dataList[position], mutableListOf())
    }

    override fun onBindViewHolder(holder: SwipeCellVH, position: Int, payloads: MutableList<Any>) {
        holder.bind(dataList[position], payloads)
    }

    override fun getItemCount(): Int = dataList.size

}

