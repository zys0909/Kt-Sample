package com.dev.zhaoys.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.zhaoys.R
import com.dev.zhaoys.app.ApiCreate
import com.dev.zhaoys.app.ExtraConst
import com.dev.zhaoys.app.TestApi
import com.dev.zhaoys.base.BaseActivity
import com.dev.zhaoys.ui.WebActivity
import com.dev.zhaoys.ui.articlelist.ArticleListActivity
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerAdapter
import com.zys.common.adapter.RecyclerSubmit
import com.zys.common.adapter.RecyclerSupport
import com.zys.common.imageload.ImageLoader
import kotlinx.android.synthetic.main.activity_wan_android_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class WanMainActivity : BaseActivity() {

    companion object {
        const val POSITION_ARTICLE_TOP = 5
        const val POSITION_ARTICLE_LIST = 10
    }

    private val model by lazy { ViewModelProviders.of(this).get(WanViewModel::class.java) }
    //    private val model by lazy { defaultViewModelProviderFactory.create(WanViewModel::class.java) }
    private val support = RecyclerSupport()
    private lateinit var adapter: RecyclerAdapter
    override fun layoutId(): Int = R.layout.activity_wan_android_main

    override fun init(savedInstanceState: Bundle?) {

        initToolbar("WanAndroid")
        support.imageLoader = ImageLoader(this)
        support.onClickCallback = { position: Int, type: Int ->
            when (type) {
                R.id.iv_follow -> {
                    val article =
                        (adapter.currentList()[position] as ArticleItem).article
                    article.collect = !article.collect
                    adapter.notifyItemChanged(position, "collect")
                }
                R.id.atv_more -> {
                    startActivity(
                        Intent(
                            this@WanMainActivity,
                            ArticleListActivity::class.java
                        )
                    )
                }
                R.id.item_article -> {
                    val article =
                        (adapter.currentList()[position] as ArticleItem).article
                    startActivity(
                        Intent(this@WanMainActivity, WebActivity::class.java)
                            .putExtra(ExtraConst.WEB_URL, article.link)
                            .putExtra(ExtraConst.ACTIVITY_TITLE, article.chapterName)
                    )
                }
                R.id.atv_title -> {

                }
            }
        }

        adapter = RecyclerAdapter(support)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        btn_home.setOnClickListener {

        }
        btn_other.setOnClickListener {
        }
        model.banner().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(listOf(BannerItem(it)), RecyclerSubmit())
            }
        })
        articleTop()
        articleList()
    }

    private fun articleTop() {
        model.articleTop().observe(this, Observer {
            it?.let {
                val list = mutableListOf<ItemCell>()
                val size = it.size
                if (size > 0) {
                    list.add(ArticleTitleItem())
                }
                for (i in 0 until size) {
                    list.add(ArticleItem(POSITION_ARTICLE_TOP + i, it[i]))
                }
                adapter.submitList(list, RecyclerSubmit())
            }
        })
    }

    private fun articleList() {
        val total = 12
        lifecycleScope.launch {
            try {
                val response = ApiCreate.build(TestApi::class.java).articleList(0)
                if (response.errorCode == 0) {
                    val list = mutableListOf<ItemCell>()
                    val temp = response.data?.datas ?: emptyList()
                    val size = if (temp.size > total) total else temp.size
                    for (i in 0 until size) {
                        list.add(ArticleItem(POSITION_ARTICLE_LIST + 1, temp[i]))
                    }
                    GlobalScope.launch(context = Dispatchers.Main, block = {
                        adapter.submitList(list, RecyclerSubmit())
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
