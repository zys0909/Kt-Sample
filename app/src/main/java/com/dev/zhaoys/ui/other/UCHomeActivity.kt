package com.dev.zhaoys.ui.other

import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.zhaoys.R
import com.dev.zhaoys.app.ApiCreate
import com.dev.zhaoys.app.TestApi
import com.dev.zhaoys.app.TestCreate
import com.dev.zhaoys.base.BaseActivity
import com.dev.zhaoys.data.PageData
import com.dev.zhaoys.extend.error
import com.dev.zhaoys.extend.requestComplete
import com.dev.zhaoys.extend.smoothToTop
import com.dev.zhaoys.ui.wanandroid.ArticleItem
import com.dev.zhaoys.widget.behavior.UCViewHeaderBehaviorNormal
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.tencent.mmkv.MMKV
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerAdapter
import com.zys.common.adapter.RecyclerSubmit
import com.zys.common.adapter.RecyclerSupport
import kotlinx.android.synthetic.main.activity_new_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 描述:仿uc首页
 *
 * author zhaoys
 * create by 2019/7/29 0029
 */
class UCHomeActivity : BaseActivity() {

    private lateinit var mainAdapter: RecyclerAdapter
    private val support = RecyclerSupport()
    private lateinit var headerBehavior: UCViewHeaderBehaviorNormal
    private var index = 0

    override fun layoutId(): Int = R.layout.activity_new_home

    override fun init(savedInstanceState: Bundle?) {
        MMKV.initialize(this)
        mainAdapter = RecyclerAdapter(support)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@UCHomeActivity)
            adapter = mainAdapter
        }
        articleList(0)
        val behavior =
            (id_uc_view_header_layout.layoutParams as CoordinatorLayout.LayoutParams).behavior
        headerBehavior = behavior as UCViewHeaderBehaviorNormal
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
        test()
    }

    private fun test() {
        GlobalScope.launch {
            try {
                val response = TestCreate.build(TestApi::class.java).testTop()
                Log.d("测试TAG", response.bytes().toString())
            } catch (e: java.lang.Exception) {
            }
        }
    }

    private fun articleList(index: Int) {
        GlobalScope.launch {
            try {
                val response = ApiCreate.build(TestApi::class.java).articleList(index)
                if (response.errorCode == 0) {
                    val list = mutableListOf<ItemCell>()
                    val temp = response.data?.datas ?: emptyList()
                    val size = temp.size
                    for (i in 0 until size) {
                        list.add(ArticleItem(0, temp[i]))
                    }
                    GlobalScope.launch(context = Dispatchers.Main, block = {
                        mainAdapter.submitList(
                            list,
                            RecyclerSubmit(index, 10, response.data?.size ?: 0)
                        )
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

    override fun onBackPressed() {
        if (headerBehavior.isClosed) {
            headerBehavior.openPager()
            recyclerView.smoothToTop()
        } else {
            super.onBackPressed()
        }
    }
}