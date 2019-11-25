package com.zys.common.adapter

import android.view.View
import com.zys.common.R

class DefaultEmptyCell : ItemCell {

    override fun layoutResId() = R.layout.item_empty_def

    override fun itemId() = "com.zys.common.adapter.DefaultEmptyCell"

    override fun itemContent() = "com.zys.common.adapter.DefaultEmptyCell"

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport) =
        DefaultEmptyVH(itemView, support)

}

class DefaultEmptyVH(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support)