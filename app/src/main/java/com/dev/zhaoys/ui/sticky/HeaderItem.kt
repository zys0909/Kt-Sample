package com.dev.zhaoys.ui.sticky

import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.View
import com.dev.zhaoys.R
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerSupport
import com.zys.common.adapter.RecyclerVH
import com.zys.common.ext.debounceClick
import kotlinx.android.synthetic.main.item_sticky_header.view.*

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019-09-30
 */
const val ITEM_HEADER = R.layout.item_sticky_header

class HeaderItem : ItemCell {

    val arr = arrayOf("Header1", "Header2", "Header3", "Header4")
    var colorTag = SparseBooleanArray()

    override fun itemContent(): String = "StickyHeader"

    override fun itemId(): String = "StickyHeader"

    override fun layoutResId(): Int = ITEM_HEADER

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH =
        HeaderItemVH(itemView, support)
}

class HeaderItemVH(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {
    init {
        itemView.debounceClick {
            support.onClickCallback?.invoke(adapterPosition, it.id)
        }
        itemView.apply {
            tv_name1.debounceClick {
                support.onClickCallback?.invoke(adapterPosition, it.id)
            }
            tv_name2.debounceClick {
                support.onClickCallback?.invoke(adapterPosition, it.id)
            }
            tv_name3.debounceClick {
                support.onClickCallback?.invoke(adapterPosition, it.id)
            }
            tv_name4.debounceClick {
                support.onClickCallback?.invoke(adapterPosition, it.id)
            }
        }
    }

    override fun bind(itemCell: ItemCell, payloads: MutableList<Any>) {
        val item = itemCell as HeaderItem
        itemView.tv_name1.apply {
            text = item.arr[0]
            setTextColor(if (item.colorTag[0]) Color.RED else Color.BLACK)
        }

        itemView.tv_name2.text = item.arr[1]
        itemView.tv_name3.text = item.arr[2]
        itemView.tv_name4.text = item.arr[3]
    }
}