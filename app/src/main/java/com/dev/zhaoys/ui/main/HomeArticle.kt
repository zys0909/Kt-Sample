package com.dev.zhaoys.ui.main

import android.annotation.SuppressLint
import android.view.View
import com.dev.zhaoys.R
import com.dev.zhaoys.data.response.ArticleData
import com.dev.zhaoys.utils.TimeUtil
import kotlinx.android.synthetic.main.item_home_article.view.*

/**
 * 描述:首页文章
 *
 * author zhaoys
 * create by 2019/7/22 0022
 */
class HomeArticle {
    class ArticleItem(val position: Int, val article: ArticleData) : MainVisitable {
        override fun uid(): String = article.id.toString()

        override fun position(): Int = position

        override fun layoutId(): Int = HomeSupport.ARTICLE
    }

    class ArticleItemVh(itemView: View, support: HomeSupport) : HomeWrapper.BaseMainViewHolder(itemView, support) {
        init {
            itemView.apply {
                setOnClickListener {
                    support.itemClick.itemClick(it, adapterPosition)
                }
                iv_follow.setOnClickListener {
                    support.itemClick.itemClick(it, adapterPosition)
                }
            }
        }

        override fun bind(data: MainVisitable, payload: List<Any>) {
            if (payload.isNotEmpty()) {
                val article = (data as ArticleItem).article
                itemView.iv_follow.setImageResource(if (article.collect) R.drawable.ic_follow_selected else R.drawable.ic_follow_normal)
            }
        }

        @SuppressLint("SetTextI18n")
        override fun bind(data: MainVisitable) {
            val article = (data as ArticleItem).article
            itemView.apply {
                iv_follow.setImageResource(if (article.collect) R.drawable.ic_follow_selected else R.drawable.ic_follow_normal)
                atv_title.text = article.title
                atv_desc.text = "作者 : ${article.author}  分类 : ${article.superChapterName} 时间 : ${article.niceDate}"
                when {
                    article.type == 1 -> {
                        iv_state.visibility = View.VISIBLE
                        iv_state.setImageResource(R.drawable.ic_state_top)
                    }
                    TimeUtil.isToday(article.publishTime) -> {
                        iv_state.visibility = View.VISIBLE
                        iv_state.setImageResource(R.drawable.ic_state_new)
                    }
                    else -> iv_state.visibility = View.GONE
                }
            }
        }
    }
}