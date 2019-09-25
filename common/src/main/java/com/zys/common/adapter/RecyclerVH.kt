package com.zys.common.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerSupport

open class RecyclerVH(itemView: View, val support: RecyclerSupport) :
    RecyclerView.ViewHolder(itemView) {

    open fun bind(itemCell: ItemCell, payloads: MutableList<Any> = mutableListOf()) {
        //empty
    }
}