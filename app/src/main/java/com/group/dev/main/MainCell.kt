package com.group.dev.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import com.group.common.adapter.ItemCell
import com.group.common.adapter.RecyclerSupport
import com.group.common.adapter.RecyclerVH
import com.group.common.core.ExtraConst
import com.group.common.ext.asClass
import com.group.common.ext.debounceClick
import com.group.common.ext.dp
import com.group.common.ext.dpf
import com.group.dev.R
import kotlinx.android.synthetic.main.item_home_normal.view.*

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/14
 */
class MainCell(val content: String, val callback: (Bundle) -> Unit = {}) :
    ItemCell {
    override fun itemContent(): String = content

    override fun itemId(): String = content

    override fun layoutResId(): Int = R.layout.item_main_cell

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH =
        MainViewHolder(itemView, support)
}

class MainViewHolder(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {
    private val tvName = itemView.findViewById<AppCompatTextView>(R.id.tv_name)
    private var block: ((Bundle) -> Unit)? = null
    private var cell: MainCell? = null

    init {
        val drawable = GradientDrawable()
        drawable.setColor(Color.TRANSPARENT)
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setStroke(2.dp, Color.GRAY)
        drawable.cornerRadius = 2.dpf
        itemView.background = drawable
        itemView.debounceClick {
            support.onClickCallback?.invoke(adapterPosition, it.id)
            val mainCell = cell ?: return@debounceClick
            mainCell.callback.invoke(bundleOf(ExtraConst.ACTIVITY_TITLE to mainCell.content))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bind(itemCell: ItemCell, payloads: MutableList<Any>) {
        cell = itemCell.asClass {
            tvName.text = "${adapterPosition + 1} - ${itemContent()}"
            block = callback
        }
    }
}