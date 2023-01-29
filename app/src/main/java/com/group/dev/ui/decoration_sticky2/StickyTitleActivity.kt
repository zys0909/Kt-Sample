package com.group.dev.ui.decoration_sticky2

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group.common.base.RecyclerViewActivity
import com.group.common.core.ColorR
import com.group.common.ext.dp
import com.group.dev.R
import com.group.dev.ui.decoration_sticky2.core.StickyDecoration

/**
 * 描述: 粘性标题实现
 *
 * RecyclerView.ItemDecoration 实现 分组 悬浮 置顶
 * <p>
 * author zys
 * create by 2021/2/16
 */
class StickyTitleActivity : RecyclerViewActivity() {
    private val recyclerAdapter by lazy { StickyDataAdapter() }

    override fun init(view: RecyclerView) {
        view.setPadding(20.dp, 10.dp, 20.dp, 0)
        view.setBackgroundColor(ColorR.C1C4CC)
        view.layoutManager = LinearLayoutManager(this)
        view.adapter = recyclerAdapter
        val stickyDecoration = StickyDecoration(view)
        stickyDecoration.setClickId(R.id.tv_delete)
        view.addItemDecoration(stickyDecoration)
        val temp = mutableListOf<DateCell>()
        for (y in 2000..2050) {
            for (m in 1..12 step 1) {
                for (d in 1..28 step 3) {
                    temp.add(DateCell("$y 年 $m 月 $d 日"))
                }
            }
            recyclerAdapter.submit(temp)
        }
    }

}
