package com.dev.zhaoys.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.zhaoys.R
import com.dev.zhaoys.base.BaseAdapter
import com.dev.zhaoys.base.IViewHolder
import com.dev.zhaoys.data.response.HomeBannerData
import com.dev.zhaoys.imageLoad.ImageLoad
import kotlinx.android.synthetic.main.item_home_banner.view.*
import kotlinx.android.synthetic.main.item_home_banner_child.view.*

/**
 * 描述:首页Banner
 *
 * author zhaoys
 * create by 2019/7/22 0022
 */
class HomeBanner {
    class BannerItem(val bannerList: List<HomeBannerData>) : MainVisitable {
        override fun uid(): String = "banner"

        override fun position(): Int = HomeSupport.POSITION_BANNER

        override fun layoutId(): Int = HomeSupport.HOME_BANNER
    }

    class BannerItemVh(itemView: View, support: HomeSupport) : HomeWrapper.BaseMainViewHolder(itemView, support) {

        private val bannerChildAdapter: BannerChildAdapter


        init {
            itemView.apply {
                rv_banner.orientation = RecyclerView.HORIZONTAL
                bannerChildAdapter = BannerChildAdapter(BannerChildSupport(support.imageLoad))
                rv_banner.adapter = bannerChildAdapter
//                ViewPageHelper(rv_banner).init()
            }
        }

        override fun bind(data: MainVisitable) {
            val homeBannerItem = data as BannerItem
            bannerChildAdapter.load(homeBannerItem.bannerList)
        }

    }

    class BannerChildAdapter(private val support: BannerChildSupport) : BaseAdapter<HomeBannerData, BannerChildVh>() {

        fun load(temp: List<HomeBannerData>) {
            list.clear()
            list.addAll(temp)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerChildVh =
            BannerChildVh(inflateView(parent, R.layout.item_home_banner_child), support)

        override fun onBindViewHolder(holder: BannerChildVh, position: Int) {
            holder.bind(list[position % list.size])
        }

        override fun getItemCount(): Int = when (list.size) {
            0, 1 -> list.size
            else -> Int.MAX_VALUE
        }

    }

    class BannerChildSupport(val imageLoad: ImageLoad<String>)

    class BannerChildVh(itemView: View, s: BannerChildSupport) :
        IViewHolder<HomeBannerData, BannerChildSupport>(itemView, s) {

        override fun bind(data: HomeBannerData) {
            itemView.apply {
                support.imageLoad.load(iv_banner, data.imagePath)
            }
        }
    }
}