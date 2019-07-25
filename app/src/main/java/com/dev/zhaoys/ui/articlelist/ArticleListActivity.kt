package com.dev.zhaoys.ui.articlelist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.zhaoys.R
import com.dev.zhaoys.http.ApiCreate
import com.dev.zhaoys.app.ExtraConst
import com.dev.zhaoys.http.TestApi
import com.dev.zhaoys.base.BaseActivity
import com.dev.zhaoys.base.OnItemClick
import com.dev.zhaoys.data.PageData
import com.dev.zhaoys.extend.error
import com.dev.zhaoys.extend.requestComplete
import com.dev.zhaoys.imageLoad.StringGlideEngine
import com.dev.zhaoys.ui.WebActivity
import com.dev.zhaoys.ui.main.HomeArticle
import com.dev.zhaoys.ui.main.HomeSupport
import com.dev.zhaoys.ui.main.MainAdapter
import com.dev.zhaoys.ui.main.MainVisitable
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_main.recyclerView
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

    private lateinit var mainAdapter: MainAdapter
    private var index = 0

    override fun layoutId(): Int = R.layout.activity_article_list

    override suspend fun init(savedInstanceState: Bundle?) {
        initToolbar("最新博文")

        mainAdapter = MainAdapter(HomeSupport(object : OnItemClick {
            override fun itemClick(view: View, position: Int) {
                val article = (mainAdapter.list[position] as HomeArticle.ArticleItem).article
                when (view.id) {
                    R.id.iv_follow -> {
                        article.collect = !article.collect
                        mainAdapter.notifyItemChanged(position, "collect")
                    }
                    R.id.item_article -> {
                        startActivity(
                            Intent(this@ArticleListActivity, WebActivity::class.java)
                                .putExtra(ExtraConst.WEB_URL, article.link)
                                .putExtra(ExtraConst.WEB_TITLE, article.chapterName)
                        )
                    }
                }
            }
        }, StringGlideEngine(this)))
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ArticleListActivity)
            adapter = mainAdapter
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
                val response = ApiCreate.build(TestApi::class.java).articleList(index)
                if (response.errorCode == 0) {
                    val list = mutableListOf<MainVisitable>()
                    val temp = response.data?.datas ?: emptyList()
                    val size = temp.size
                    for (i in 0 until size) {
                        list.add(HomeArticle.ArticleItem(0, temp[i]))
                    }
                    GlobalScope.launch(context = Dispatchers.Main, block = {
                        mainAdapter.refresh(list, index == 0)
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