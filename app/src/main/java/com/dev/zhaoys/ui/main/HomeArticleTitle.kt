package com.dev.zhaoys.ui.main

import android.view.View
import kotlinx.android.synthetic.main.item_home_article_title.view.*

/**
 * 描述:文章标题
 *
 * author zhaoys
 * create by 2019/7/23 0023
 */
class HomeArticleTitle {
    class ArticleTitleItem : MainVisitable {
        override fun uid(): String = "title"

        override fun position(): Int = HomeSupport.POSITION_ARTICLE_TOP - 1

        override fun layoutId(): Int = HomeSupport.ARTICLE_TITLE
    }

    class ArticleTitleVh(itemView: View, support: HomeSupport) : HomeWrapper.BaseMainViewHolder(itemView, support) {
        init {
            itemView.apply {
                atv_more.setOnClickListener {
                    support.itemClick.itemClick(it, adapterPosition)
                }
                atv_title.setOnClickListener {
                    support.itemClick.itemClick(it, adapterPosition)
                }
            }
        }

        override fun bind(data: MainVisitable) {

        }
    }
}