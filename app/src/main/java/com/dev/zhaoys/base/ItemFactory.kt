package com.dev.zhaoys.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * ItemFactory
 *
 */
interface ItemFactory<VH : RecyclerView.ViewHolder> {

    fun createViewHolder(viewType: Int, itemView: View): VH?
}