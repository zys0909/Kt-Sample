package com.group.common.adapter
import android.view.View
import androidx.annotation.LayoutRes

interface ItemCell {

    @LayoutRes
    fun layoutResId(): Int

    /**
     * item标志，用于比较item是否一样
     */
    fun itemId(): String

    /**
     * item内容
     */
    fun itemContent(): String

    fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH
}