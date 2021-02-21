package com.group.dev.ui.tantan

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.group.dev.R
import com.group.common.imageload.ImageLoader

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/21
 */
class PrettyCardCell(val name: String, val url: String)

class PrettyCardVH(itemView: View, private val imageLoader: ImageLoader) :
    RecyclerView.ViewHolder(itemView) {
    private val imageView = itemView.findViewById<AppCompatImageView>(R.id.image_view)
    private val textName = itemView.findViewById<AppCompatTextView>(R.id.text_name)
    fun bind(itemCell: PrettyCardCell) {
        imageLoader.load(imageView, itemCell.url)
        textName.text = itemCell.name
    }
}