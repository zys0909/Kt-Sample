package com.dev.zhaoys.ui.main

import android.view.View
import com.dev.zhaoys.R
import com.dev.zhaoys.base.ItemFactory
import com.dev.zhaoys.base.OnItemClick
import com.dev.zhaoys.imageLoad.ImageLoad

class HomeSupport(val itemClick: OnItemClick, val imageLoad: ImageLoad<String>) :
    ItemFactory<HomeWrapper.BaseMainViewHolder> {
    companion object {
        const val HOME_BANNER = R.layout.item_home_banner
        const val ARTICLE = R.layout.item_home_article
        const val ARTICLE_TITLE = R.layout.item_home_article_title

        const val POSITION_BANNER = 1
        const val POSITION_ARTICLE_TOP = 5
        const val POSITION_ARTICLE_LIST = 10
    }

    override fun createViewHolder(viewType: Int, itemView: View): HomeWrapper.BaseMainViewHolder =
        when (viewType) {
            HOME_BANNER -> HomeBanner.BannerItemVh(itemView, this)
            ARTICLE -> HomeArticle.ArticleItemVh(itemView, this)
            ARTICLE_TITLE->HomeArticleTitle.ArticleTitleVh(itemView, this)
            else -> HomeBanner.BannerItemVh(itemView, this)
        }
}