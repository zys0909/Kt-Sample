package com.zys.common.adapter

import android.view.View
import com.zys.common.R
import com.zys.ext.debounceClick

class DefaultErrorCell : ItemCell {

    override fun layoutResId() = R.layout.item_error_def

    override fun itemId() = "com.zys.common.adapter.DefaultErrorCell"

    override fun itemContent() = "com.zys.common.adapter.DefaultErrorCell"

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport) =
        DefaultErrorVH(itemView, support)

}

class DefaultErrorVH(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {

    init {
        itemView.debounceClick {
            support.onRetry?.invoke()
        }
    }
}