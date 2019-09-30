package com.dev.zhaoys.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.zhaoys.R
import com.dev.zhaoys.base.BaseActivity
import com.dev.zhaoys.ui.TestHomeActivity
import com.dev.zhaoys.ui.main.WanMainActivity
import com.dev.zhaoys.ui.other.TestImageActivity
import com.dev.zhaoys.ui.other.TouchActivity
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerAdapter
import com.zys.common.adapter.RecyclerSubmit
import com.zys.common.adapter.RecyclerSupport
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    private lateinit var adapter: RecyclerAdapter
    override fun layoutId(): Int = R.layout.activity_home

    override fun init(savedInstanceState: Bundle?) {
        initToolbar("Home")
        val support = RecyclerSupport()
        support.onClickCallback = { position: Int, type: Int ->
            when (adapter.currentList()[position].itemId()) {
                "0" -> startActivity(Intent(this, WanMainActivity::class.java))
                "1" -> startActivity(Intent(this, TestHomeActivity::class.java))
                "2" -> startActivity(Intent(this, TestImageActivity::class.java))
                "3" -> startActivity(Intent(this, TouchActivity::class.java))
                else -> {
                }
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this,RecyclerView.VERTICAL))
        adapter = RecyclerAdapter(support)
        recyclerView.adapter = adapter
        val list = mutableListOf<ItemCell>()
        list.add(HomeItem("0", "WanAndroid"))
        list.add(HomeItem("1", "仿UC首页"))
        list.add(HomeItem("2", "图片顶部裁剪"))
        list.add(HomeItem("3", "悬浮测试"))
        adapter.submitList(list, RecyclerSubmit(0, 10, list.size))
    }

}
