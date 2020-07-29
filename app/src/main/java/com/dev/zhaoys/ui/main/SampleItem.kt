package com.dev.zhaoys.ui.main

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.dev.zhaoys.R
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerSupport
import com.zys.common.adapter.RecyclerVH
import com.zys.common.ext.asClass
import com.zys.common.ext.debounceClick
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
        drawable.setStroke(DensityUtil.dp2px(2f), Color.GRAY)
        drawable.cornerRadius = DensityUtil.dp2px(2f).toFloat()
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