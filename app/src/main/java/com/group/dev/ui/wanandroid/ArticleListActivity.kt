package com.group.dev.ui.wanandroid

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.group.dev.R
import com.group.dev.api.ApiService
import com.group.common.core.ExtraConst
import com.group.common.base.BaseActivity
import com.group.dev.data.PageData
import com.group.dev.error
import com.group.dev.requestComplete
import com.group.dev.ui.WebActivity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.group.common.adapter.ItemCell
import com.group.common.adapter.RecyclerAdapter
import com.group.common.adapter.RecyclerSupport
import kotlinx.android.synthetic.main.activity_wan_android_main.recyclerView
import kotlinx.android.synthetic.main.layout_smart_refresh.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 描述:文章列表
 *
 * author zhaoys
 * create by 2019/7/23 0023
 */
class ArticleListActivity : BaseActivity() {

    private lateinit var adapter: RecyclerAdapter
    private val support = RecyclerSupport()
    private var index = 0

    override fun layoutId(): Int = R.layout.activity_article_list

    override  fun init(savedInstanceState: Bundle?) {
        initToolbar("最新博文")

        support.onClickCallback = { position: Int, type: Int ->
            when (type) {
                R.id.iv_follow -> {
                    val article =
                        (adapter.currentList[position] as ArticleItem).article
                    article.collect = !article.collect
                    adapter.notifyItemChanged(position, "collect")
                }

                R.id.item_article -> {
                    val article =
                        (adapter.currentList[position] as ArticleItem).article
                    startActivity(
                        Intent(this, WebActivity::class.java)
                            .putExtra(ExtraConst.WEB_URL, article.link)
                            .putExtra(ExtraConst.ACTIVITY_TITLE, article.chapterName)
                    )
                }
                R.id.atv_title -> {

                }
            }
        }

        adapter = RecyclerAdapter(support)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ArticleListActivity)
            adapter = adapter
        }
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                index++
                articleList(index)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                index = 0
                articleList(0)
            }
        })
        articleList(0)
    }

    private fun articleList(index: Int) {
        GlobalScope.launch {
            try {
                val response = ApiService.testApi.articleList(index)
                if (response.errorCode == 0) {
                    val list = mutableListOf<ItemCell>()
                    val temp = response.data?.datas ?: emptyList()
                    val size = temp.size
                    for (i in 0 until size) {
                        list.add(ArticleItem(0, temp[i]))
                    }
                    GlobalScope.launch(context = Dispatchers.Main, block = {
                        adapter.submit(list)
                        val pageData = response.data?.let {
                            PageData(it.curPage, it.size, it.total)
                        }
                        smartRefreshLayout.requestComplete(pageData)
                    })
                } else {
                    smartRefreshLayout.error(index + 1)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                smartRefreshLayout.error(index + 1)
            }
        }
    }
}