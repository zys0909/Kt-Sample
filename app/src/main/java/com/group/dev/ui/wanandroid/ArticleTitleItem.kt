package com.group.dev.ui.wanandroid

import android.view.View
import com.group.dev.R
import com.group.common.adapter.ItemCell
import com.group.common.adapter.RecyclerSupport
import com.group.common.adapter.RecyclerVH
import com.group.common.ext.debounceClick
import kotlinx.android.synthetic.main.item_home_article_title.view.*

/**
 * 描述:文章标题
 *
 * author zhaoys
 * create by 2019/7/23 0023
 */
class ArticleTitleItem : ItemCell {
    override fun itemContent(): String = "title"

    override fun itemId(): String = "title"

    override fun layoutResId(): Int = R.layout.item_home_article_title

    override fun onCreateViewHolder(itemView: View, support: RecyclerSupport): RecyclerVH =
        ArticleTitleVh(itemView, support)
}

class ArticleTitleVh(itemView: View, support: RecyclerSupport) :
    RecyclerVH(itemView, support) {
    init {
        itemView.apply {
            atv_more.debounceClick {
                support.onClickCallback?.invoke(adapterPosition, it.id)
            }
            atv_title.debounceClick {
                support.onClickCallback?.invoke(adapterPosition, it.id)
            }
        }
    }
}