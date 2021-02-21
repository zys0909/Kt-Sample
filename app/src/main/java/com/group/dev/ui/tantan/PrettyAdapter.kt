package com.group.dev.ui.tantan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.zhaoys.R
import com.zys.common.imageload.ImageLoader

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/21
 */
class PrettyAdapter(private val imageLoader: ImageLoader) : RecyclerView.Adapter<PrettyCardVH>() {
    val dataList = mutableListOf<PrettyCardCell>()


    fun submit(list: List<PrettyCardCell>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrettyCardVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pretty_card, parent, false)
        return PrettyCardVH(view, imageLoader)
    }

    override fun onBindViewHolder(holder: PrettyCardVH, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}