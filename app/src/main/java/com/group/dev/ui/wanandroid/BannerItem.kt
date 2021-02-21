package com.group.dev.ui.wanandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.group.dev.R
import com.group.dev.data.response.HomeBannerData
import com.group.common.adapter.ItemCell
import com.group.common.adapter.RecyclerSupport
import com.group.common.adapter.RecyclerVH
import kotlinx.android.synthetic.main.item_home_banner.view.*
import kotlinx.android.synthetic.main.item_home_banner_child.view.*

/**
 * 描述:首页Banner
 *
 * author zhaoys
 * create by 2019/7/22 0022
 */
class BannerItem(val bannerList: List<HomeBannerData>) : ItemCell {
    override fun itemContent(): String = "banner"

    override fun itemId(): String = "banner"

    override fun layoutResId(): Int = R.layout.item_home_banner

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH =
        BannerItemVh(itemView, support)
}

class BannerItemVh(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {

    private val bannerChildAdapter: BannerChildAdapter


    init {
        itemView.apply {
            rv_banner.orientation = RecyclerView.HORIZONTAL
            bannerChildAdapter = BannerChildAdapter(support)
            rv_banner.adapter = bannerChildAdapter
//                ViewPageHelper(rv_banner).init()
        }
    }

    override fun bind(itemCell: ItemCell, payloads: MutableList<Any>) {
        val homeBannerItem = itemCell as BannerItem
        bannerChildAdapter.load(homeBannerItem.bannerList)
    }
}

class BannerChildAdapter(private val support: RecyclerSupport) :
    RecyclerView.Adapter<BannerChildVh>() {
    val list = ArrayList<HomeBannerData>()
    fun load(temp: List<HomeBannerData>) {
        list.clear()
        list.addAll(temp)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerChildVh =
        BannerChildVh(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_banner_child, parent, false), support
        )

    override fun onBindViewHolder(holder: BannerChildVh, position: Int) {
        holder.bind(list[position % list.size])
    }

    override fun getItemCount(): Int = when (list.size) {
        0, 1 -> list.size
        else -> Int.MAX_VALUE
    }

}

class BannerChildVh(itemView: View, val support: RecyclerSupport) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(data: HomeBannerData) {
        support.imageLoader.load(itemView.iv_banner, data.imagePath)
    }
}