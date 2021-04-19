package com.group.dev.ui.item_swipe.demo

import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group.common.adapter.RecyclerSupport
import com.group.common.base.RecyclerViewActivity
import com.group.common.core.ExtraConst
import com.group.common.ext.openActivity
import com.group.dev.R
import com.group.dev.ui.fish.FishActivity
import com.group.dev.ui.item_swipe.ItemSwipeHelper

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
                }

                R.id.tv_delete -> {
                    recyclerAdapter.removeAt(position)
                    recyclerAdapter.notifyItemRemoved(position)
                }
                else -> openActivity<FishActivity>(bundleOf(ExtraConst.ACTIVITY_TITLE to "灵动的锦鲤"))
            }
        }

        view.layoutManager = LinearLayoutManager(this)
        view.adapter = recyclerAdapter
        view.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        ItemSwipeHelper.attach(this,view)

        val temp = mutableListOf<String>()
        for (y in 2018..2021) {
            for (m in 1..12 step 2) {
                for (d in 1..28 step 3) {
                    temp.add("$y 年 $m 月 $d 日")
                }
            }
        }
        val data = temp.mapIndexed { index, s -> SwipeCell(s, (index + 1) % 3) }
        recyclerAdapter.submit(data)
    }
}
