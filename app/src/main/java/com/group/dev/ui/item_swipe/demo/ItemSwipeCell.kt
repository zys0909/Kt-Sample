package com.group.dev.ui.item_swipe.demo

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.group.common.adapter.ItemCell
import com.group.common.adapter.RecyclerSupport
import com.group.common.adapter.RecyclerVH
import com.group.common.ext.debounceClick
import com.group.common.ext.dp
import com.group.dev.R
import com.group.dev.ui.item_swipe.ItemSwipe

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
class SwipeCell(val text: String, val type: Int) : ItemCell {


    override fun layoutResId() = when (type) {
        1 -> R.layout.item_date_cell_delete_2
        2 -> R.layout.item_date_cell_delete_3
        else -> R.layout.item_date_cell_delete_1
    }

    override fun itemId() = text

    override fun itemContent() = text

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH {
        return when (type) {
            1 -> SwipeCellVH2(itemView, support)
            2 -> SwipeCellVH3(itemView, support)
            else -> SwipeCellVH1(itemView, support)
        }
    }

}

class SwipeCellVH1(itemView: View, support: RecyclerSupport) : BaseSwipeCellVH(itemView, support) {
    override fun maxSwipeWidth(): Int = 80.dp
}

class SwipeCellVH2(itemView: View, support: RecyclerSupport) : BaseSwipeCellVH(itemView, support) {
    override fun maxSwipeWidth(): Int = 80.dp * 2
}

class SwipeCellVH3(itemView: View, support: RecyclerSupport) : BaseSwipeCellVH(itemView, support) {
    override fun maxSwipeWidth(): Int = 80.dp * 3
}

abstract class BaseSwipeCellVH(itemView: View, support: RecyclerSupport) :
    RecyclerVH(itemView, support), ItemSwipe {

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

    override fun enable(): Boolean = true
}