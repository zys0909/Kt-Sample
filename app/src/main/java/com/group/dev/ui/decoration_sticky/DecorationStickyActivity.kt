package com.group.dev.ui.decoration_sticky;

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.zhaoys.R
import com.dev.zhaoys.app.BaseActivity

/**
 * 描述: RecyclerView.ItemDecoration 实现 分组 悬浮 置顶
 * <p>
 * author zys
 * create by 2021/2/16
 */
class DecorationStickyActivity : BaseActivity() {
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val recyclerAdapter by lazy { DecorationAdapter() }

    override fun layoutId(): Int = R.layout.activity_sample_recyclerview

    override fun init(savedInstanceState: Bundle?) {
        initToolbar("DecorationSticky")
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter
        recyclerView.addItemDecoration(StickyDecoration())
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
