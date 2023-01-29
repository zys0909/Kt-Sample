package com.group.dev.ui.decoration_sticky2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.group.dev.R
import com.group.dev.ui.decoration_sticky2.core.AbstractHeaderHolder
import com.group.dev.ui.decoration_sticky2.core.ISticky

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
class StickyDataAdapter : RecyclerView.Adapter<DateVH>(), ISticky {

    private val dataList = mutableListOf<DateCell>()


    fun submit(list: List<DateCell>) {
        dataList.clear()
        dataList.addAll(list)
        notifyItemRangeChanged(0, itemCount)
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
                (position > 0 && getGroupId(position - 1) != getGroupId(position))


    override fun getGroupId(position: Int): String = dataList[position].month

    override fun onCreateHeaderViewHolder(parent: ViewGroup): AbstractHeaderHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_header_cell, parent, false)
        return HeaderHeaderHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: AbstractHeaderHolder, position: Int, isFirstItem: Boolean) {
        holder.bind(dataList[position],isFirstItem)
    }
}

