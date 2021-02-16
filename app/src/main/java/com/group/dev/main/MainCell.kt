package com.group.dev.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.dev.zhaoys.R
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerSupport
import com.zys.common.adapter.RecyclerVH
import com.zys.ext.asClass
import com.zys.ext.debounceClick
import com.zys.ext.dp
import com.zys.ext.dpf
import kotlinx.android.synthetic.main.item_home_normal.view.*

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/14
 */
class MainCell(val content: String, val callback: () -> Unit = {}) :
    ItemCell {
    override fun itemContent(): String = content

    override fun itemId(): String = content

    override fun layoutResId(): Int = R.layout.item_main_cell

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH =
        MainViewHolder(itemView, support)
}

class MainViewHolder(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {
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

    @SuppressLint("SetTextI18n")
    override fun bind(itemCell: ItemCell, payloads: MutableList<Any>) {
        itemCell.asClass<MainCell> {
            itemView.tv_name.text = "${adapterPosition + 1} - ${itemContent()}"
            block = callback
        }
    }
}