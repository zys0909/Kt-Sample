package com.group.dev.ui.decoration_sticky

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group.common.base.RecyclerViewActivity
import com.group.common.core.ColorR
import com.group.common.ext.dp

/**
 * 描述: RecyclerView.ItemDecoration 实现 分组 悬浮 置顶
 * <p>
 * author zys
 * create by 2021/2/16
 */
class DecorationStickyActivity : RecyclerViewActivity() {
    private val recyclerAdapter by lazy { DecorationAdapter() }

    override fun init(view: RecyclerView) {
        view.setPadding(20.dp, 80.dp, 20.dp, 0)
        view.setBackgroundColor(ColorR.C1C4CC)
        view.layoutManager = LinearLayoutManager(this)
        view.adapter = recyclerAdapter
        view.addItemDecoration(StickyDecoration(ColorR.BBBBBB))
        val temp = mutableListOf<DateCell>()
        for (y in 2018..2021) {
            for (m in 1..12 step 2) {
                for (d in 1..28 step 3) {
                    temp.add(DateCell("$y 年 $m 月 $d 日"))
                }
            }
            recyclerAdapter.submit(temp)
        }
    }

}
