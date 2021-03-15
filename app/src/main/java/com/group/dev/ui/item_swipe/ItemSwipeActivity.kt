package com.group.dev.ui.item_swipe

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group.common.adapter.RecyclerSupport
import com.group.common.base.RecyclerViewActivity
import com.group.common.ext.dp
import com.group.common.ext.toast
import com.group.dev.R

/**
 * 描述:RecyclerView. 侧滑删除
 *
 * author zys
 * create by 2021/3/15
 */
class ItemSwipeActivity : RecyclerViewActivity() {

    private val support: RecyclerSupport = RecyclerSupport()
    private val recyclerAdapter by lazy { SwipeAdapter(support) }

    override fun init(view: RecyclerView) {
        support.onClickCallback = { position, type ->
            when (type) {
                R.id.tv_date -> {
                    toast(recyclerAdapter.dataList[position].itemId())
                }
                R.id.tv_delete -> {
                    recyclerAdapter.dataList.removeAt(position)
                    recyclerAdapter.notifyItemRemoved(position)
                }
            }
        }

        view.layoutManager = LinearLayoutManager(this)
        view.adapter = recyclerAdapter
        view.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        view.setSwipeHelper(88.dp)
        val temp = mutableListOf<SwipeCell>()
        for (y in 2018..2021) {
            for (m in 1..12 step 2) {
                for (d in 1..28 step 3) {
                    temp.add(SwipeCell("$y 年 $m 月 $d 日"))
                }
            }
        }
        recyclerAdapter.submit(temp)
    }
}
