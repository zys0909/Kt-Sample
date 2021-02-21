package com.group.dev.ui.sticky

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.group.dev.R
import com.group.common.base.BaseActivity
import com.group.common.ext.toast
import com.group.dev.ui.HomeItem
import com.group.common.adapter.ItemCell
import com.group.common.adapter.RecyclerAdapter
import com.group.common.adapter.RecyclerSupport
import kotlinx.android.synthetic.main.activity_sticky_recyclerview.*

class StickyTop1Activity : BaseActivity() {
    private lateinit var adapter: RecyclerAdapter
    private lateinit var manager: LinearLayoutManager
    override fun layoutId(): Int = R.layout.activity_sticky_recyclerview

    override fun init(savedInstanceState: Bundle?) {
        initToolbar("StickyRecycler")
        val support = RecyclerSupport()
        support.onClickCallback = { position: Int, type: Int ->
            toast("position = $position , type = $type")
            when (type) {
                R.id.tv_name1 -> {
                    scrollToTop(position)
                }
                R.id.tv_name2 -> {
                    if (position >= 0 && position < adapter.currentList.size) {
                        scrollToTop(position)
                        val itemCell = adapter.currentList[position] as HeaderItem
                        val src = itemCell.arr[1]
                        itemCell.arr[1] = src + 1
                        adapter.notifyItemChanged(position)
                    }
                }
            }
        }
        adapter = RecyclerAdapter(support)
        manager = LinearLayoutManager(this)
        manager.findFirstVisibleItemPosition()
        qm_recyclerView.setLayoutManager(manager)
        qm_recyclerView.addItemDecoration(
            StickyItemDecoration(qm_recyclerView.stickySectionWrapView, adapter, ITEM_HEADER)
        )
        qm_recyclerView.setAdapter(adapter)

        val list = mutableListOf<ItemCell>()
        for (i in 0..50) {
            list.add(HomeItem(i, "Item$i"))
        }
        list.add(5, HeaderItem())
        adapter.submit(list)
    }

    private fun scrollToTop(position: Int) {
        qm_recyclerView.recyclerView.scrollToPosition(position)
        manager.scrollToPositionWithOffset(position, 0)
    }

    private fun modify() {
        val currentList = adapter.currentList
        var headerPosition = -1
        for (i in 0 until currentList.size) {
            if (currentList[i] is HeaderItem) {
                headerPosition = i
                break
            }
        }
        (currentList[headerPosition] as HeaderItem).arr
    }

}
