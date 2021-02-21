package com.group.dev.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.group.dev.R
import com.group.common.adapter.ItemCell
import com.group.common.adapter.RecyclerSupport
import com.group.common.adapter.RecyclerVH
import com.group.common.ext.asClass
import com.group.common.ext.debounceClick
import com.group.common.ext.dp
import com.group.common.ext.dpf
import kotlinx.android.synthetic.main.item_home_normal.view.*

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019-09-27
 */
class HomeItem(
    var id: Int,
    var content: String? = null,
    val callback: () -> Unit = {}
) :
    ItemCell {
    override fun itemContent(): String =
        if (content.isNullOrEmpty()) "Item $id" else content.toString()

    override fun itemId(): String = id.toString()

    override fun layoutResId(): Int = R.layout.item_home_normal

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH =
        HomeItemViewHolder(itemView, support)
}

class HomeItemViewHolder(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {
    private var block: (() -> Unit)? = null

    init {
        val drawable = GradientDrawable()
        drawable.setColor(Color.TRANSPARENT)
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setStroke(2.dp, Color.GRAY)
        drawable.cornerRadius = 2.dpf
        itemView.background = drawable
        itemView.debounceClick {
            support.onClickCallback?.invoke(adapterPosition, it.id)
            block?.invoke()
        }
    }

    override fun bind(itemCell: ItemCell, payloads: MutableList<Any>) {
        itemCell.asClass<HomeItem> {
            itemView.tv_name.text = itemContent()
            itemView.img_icon.visibility = View.GONE
            block = callback
        }
    }
}