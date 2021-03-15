package com.group.dev.ui.item_swipe

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.group.common.adapter.ItemCell
import com.group.common.adapter.RecyclerSupport
import com.group.common.adapter.RecyclerVH
import com.group.common.ext.debounceClick
import com.group.dev.R

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
class SwipeCell(val text: String) : ItemCell {


    override fun layoutResId() = R.layout.item_date_cell

    override fun itemId() = text

    override fun itemContent() = text

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH {
        return SwipeCellVH(itemView, support)
    }

}

class SwipeCellVH(itemView: View, support: RecyclerSupport) :
    RecyclerVH(itemView, support) {

    private val dateView = itemView.findViewById<TextView>(R.id.tv_date)
    private val tvDelete = itemView.findViewById<TextView>(R.id.tv_delete)

    init {

        itemView.debounceClick {
            support.onClickCallback?.invoke(adapterPosition, it.id)
        }
        tvDelete.debounceClick {
            support.onClickCallback?.invoke(adapterPosition, it.id)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(itemCell: ItemCell, payloads: MutableList<Any>) {
        dateView.text = "$adapterPosition - ${itemCell.itemContent()}"
    }

}