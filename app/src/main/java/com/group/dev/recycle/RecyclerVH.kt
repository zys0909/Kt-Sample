package com.group.dev.recycle

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 描述:
 *
 * author zys
 * create by 2021/1/22
 */
abstract class RecyclerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(itemCell: ItemCell)
}