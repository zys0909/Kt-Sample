package com.group.dev.ui.decoration_sticky

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
class DecorationAdapter : RecyclerView.Adapter<DateVH>(), ISticky {

    private val dataList = mutableListOf<DateCell>()


    fun submit(list: List<DateCell>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateVH {
        val view = AppCompatTextView(parent.context)
        return DateVH(view)
    }

    override fun onBindViewHolder(holder: DateVH, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    override fun isGroupHeader(position: Int): Boolean =
        position == 0 ||
                (position > 0 && getGroupName(position - 1) != getGroupName(position))


    override fun getGroupName(position: Int): String = dataList[position].month

}

