package com.group.dev.ui.wanandroid

import android.annotation.SuppressLint
import android.view.View
import com.group.dev.R
import com.group.dev.data.response.ArticleData
import com.group.dev.utils.TimeUtil
import com.group.common.adapter.ItemCell
import com.group.common.adapter.RecyclerSupport
import com.group.common.adapter.RecyclerVH
import kotlinx.android.synthetic.main.item_home_article.view.*

/**
 * 描述:首页文章
 *
 * author zhaoys
 * create by 2019/7/22 0022
 */
class ArticleItem(val position: Int, val article: ArticleData) : ItemCell {
    override fun itemContent(): String = "article"

    override fun itemId(): String = article.id.toString()

    override fun layoutResId(): Int = R.layout.item_home_article

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH =
        ArticleItemVh(itemView, support)


    class ArticleItemVh(itemView: View, support: RecyclerSupport) : RecyclerVH(itemView, support) {
        init {
            itemView.apply {
                setOnClickListener {
                    support.onClickCallback?.invoke(adapterPosition, it.id)
                }
                iv_follow.setOnClickListener {
                    support.onClickCallback?.invoke(adapterPosition, it.id)
                }
            }
        }

        override fun bind(itemCell: ItemCell, payloads: MutableList<Any>) {
            if (payloads.isNotEmpty()) {
                val article = (itemCell as ArticleItem).article
                itemView.iv_follow.setImageResource(if (article.collect) R.drawable.ic_follow_selected else R.drawable.ic_follow_normal)
            } else {
                bind(itemCell)
            }
        }


        @SuppressLint("SetTextI18n")
        fun bind(itemCell: ItemCell) {
            val article = (itemCell as ArticleItem).article
            itemView.apply {
                iv_follow.setImageResource(if (article.collect) R.drawable.ic_follow_selected else R.drawable.ic_follow_normal)
                atv_title.text = article.title
                atv_desc.text =
                    "作者 : ${article.author}  分类 : ${article.superChapterName} 时间 : ${article.niceDate}"
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