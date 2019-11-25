package com.dev.zhaoys.ui.home

import android.view.View
import com.dev.zhaoys.R
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerSupport
import com.zys.common.adapter.RecyclerVH
import com.zys.common.ext.debounceClick
import kotlinx.android.synthetic.main.item_home_normal.view.*

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019-09-27
 */
class HomeItem(var id: String, var content: String) : ItemCell {
    override fun itemContent(): String = content

    override fun itemId(): String = id

    override fun layoutResId(): Int = R.layout.item_home_normal

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH =
        HomeItemViewHolder(itemView, support)
}

class HomeItemViewHolder(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {
    init {
        itemView.debounceClick {
            support.onClickCallback?.invoke(adapterPosition,it.id)
        }
    }
    override fun bind(itemCell: ItemCell, payloads: MutableList<Any>) {
        itemView.tv_name.text = itemCell.itemContent()
    }
}