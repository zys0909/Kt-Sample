package com.group.common.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout



inline fun createAdapter(support: RecyclerSupport.() -> Unit): RecyclerAdapter {
    return RecyclerAdapter(RecyclerSupport().apply(support))
}

@Suppress("DEPRECATION")
fun RecyclerView.autoRefresh(target: Int = 3) {
    val layoutManager = this.layoutManager
    if (layoutManager is LinearLayoutManager) {
        val position = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (position > -1) {
            if (position > target) {
                layoutManager.scrollToPosition(target)
            }
            smoothScrollToPosition(0)
        }
    }
    val parent = this.parent
    if (parent is SmartRefreshLayout) {
        parent.autoRefresh(400)
    }
}

fun SmartRefreshLayout.loadComplete(pageIndex: Int, total: Int, pageCount: Int = 10) {
    if (total == 0) {
        when (pageIndex) {
            1 -> finishRefresh()
            else -> finishLoadMore()
        }
    } else {
        val noMoreData = pageIndex * pageCount >= total
        when (pageIndex) {
            1 -> {
                finishRefresh()
                postDelayed({
                    setNoMoreData(noMoreData)
                }, 400)
            }
            else -> {
                if (noMoreData) {
                    finishLoadMoreWithNoMoreData()
                } else {
                    finishLoadMore()
                }
            }
        }
    }
}
