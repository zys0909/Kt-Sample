package com.dev.zhaoys.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.zhaoys.R
import com.dev.zhaoys.http.ApiCreate
import com.dev.zhaoys.app.ExtraConst
import com.dev.zhaoys.http.TestApi
import com.dev.zhaoys.base.BaseActivity
import com.dev.zhaoys.base.OnItemClick
import com.dev.zhaoys.http.DynamicBaseUrl
import com.dev.zhaoys.imageLoad.StringGlideEngine
import com.dev.zhaoys.ui.WebActivity
import com.dev.zhaoys.ui.articlelist.ArticleListActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    private val model by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private lateinit var mainAdapter: MainAdapter
    override fun layoutId(): Int = R.layout.activity_main

    override suspend fun init(savedInstanceState: Bundle?) {
        mainAdapter = MainAdapter(HomeSupport(object : OnItemClick {
            override fun itemClick(view: View, position: Int) {
                when (view.id) {
                    R.id.iv_follow -> {
                        val article = (mainAdapter.list[position] as HomeArticle.ArticleItem).article
                        article.collect = !article.collect
                        mainAdapter.notifyItemChanged(position, "collect")
                    }
                    R.id.atv_more -> {
                        startActivity(Intent(this@MainActivity, ArticleListActivity::class.java))
                    }
                    R.id.item_article -> {
                        val article = (mainAdapter.list[position] as HomeArticle.ArticleItem).article
                        startActivity(
                            Intent(this@MainActivity, WebActivity::class.java)
                                .putExtra(ExtraConst.WEB_URL, article.link)
                                .putExtra(ExtraConst.WEB_TITLE, article.chapterName)
                        )
                    }
                    R.id.atv_title -> {
                        lifecycleScope.launch(context = Dispatchers.IO, block = {
                            DynamicBaseUrl.test()
                        })
                    }
                }
            }
        }, StringGlideEngine(this)))
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }/*.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))*/
        model.banner().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                mainAdapter.loadModule(HomeBanner.BannerItem(it))
            }
        })
        articleTop()
        articleList()
    }

    private fun articleTop() {
        model.articleTop().observe(this, Observer {
            it?.let {
                val list = mutableListOf<MainVisitable>()
                val size = it.size
                if (size > 0) {
                    list.add(HomeArticleTitle.ArticleTitleItem())
                }
                for (i in 0 until size) {
                    list.add(HomeArticle.ArticleItem(HomeSupport.POSITION_ARTICLE_TOP + i, it[i]))
                }
                mainAdapter.loadModules(list)
            }
        })
    }

    private fun articleList() {
        lifecycleScope.launch {
            try {
                val response = ApiCreate.build(TestApi::class.java).articleList(0)
                if (response.errorCode == 0) {
                    val list = mutableListOf<MainVisitable>()
                    val temp = response.data?.datas ?: emptyList()
                    val size = if (temp.size > 4) 4 else temp.size
                    for (i in 0 until size) {
                        list.add(HomeArticle.ArticleItem(HomeSupport.POSITION_ARTICLE_LIST + 1, temp[i]))
                    }
                    GlobalScope.launch(context = Dispatchers.Main, block = {
                        mainAdapter.loadModules(list)
                    })
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private var lastClickTime = 0L
    override fun onBackPressed() {
        val now = System.currentTimeMillis()
        if (now - lastClickTime > 1000L) {
            lastClickTime = now
            toast("再按一次退出")
        } else {
            super.onBackPressed()
        }
    }
}
