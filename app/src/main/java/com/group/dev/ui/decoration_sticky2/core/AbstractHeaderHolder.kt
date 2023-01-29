package com.group.dev.ui.decoration_sticky2.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.group.dev.ui.decoration_sticky2.DateCell

/**
 * 描述:
 *
 * author ZhaoYingShu
 * create by 2022/7/22
 */
abstract class AbstractHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(itemCell: DateCell, isFirst: Boolean)
}