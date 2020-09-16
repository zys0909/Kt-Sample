package com.dev.zhaoys.ui.main

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.zhaoys.R
import com.dev.zhaoys.app.BaseActivity
import com.dev.zhaoys.extend.openActivity
import com.dev.zhaoys.ui.hook.HookActivity
import com.dev.zhaoys.ui.other.*
import com.dev.zhaoys.ui.qmhome.StickyTop2Activity
import com.dev.zhaoys.ui.sticky.StickyTop1Activity
import com.dev.zhaoys.ui.wanandroid.WanMainActivity
import com.dev.zhaoys.utils.SysUtil
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerAdapter
import com.zys.common.adapter.RecyclerSubmit
import com.zys.common.adapter.RecyclerSupport
import com.zys.common.imageload.ImageLoader
import kotlinx.android.synthetic.main.activity_sample_list.*

/**
 * 描述:首页
 *
 * author zys
 */
class MainActivity : BaseActivity() {
    private lateinit var adapter: RecyclerAdapter

    override fun layoutId(): Int = R.layout.activity_sample_list

    override fun init(savedInstanceState: Bundle?) {
        initToolbar("Home", false)
        val support = RecyclerSupport()
        support.imageLoader = ImageLoader(this)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = RecyclerAdapter(support)
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = true
        val list = mutableListOf<ItemCell>()
        list.add(HomeItem(0, "WanAndroid") {
            openActivity(
                WanMainActivity::class.java
            )
        })
        list.add(HomeItem(1, "仿UC首页") {
            openActivity(
                UCHomeActivity::class.java
            )
        })
        list.add(HomeItem(2, "图片裁剪") {
            openActivity(
                ClipImageActivity::class.java
            )
        })
        list.add(HomeItem(3, "Ruler-View") {
            openActivity(
                RulerActivity::class.java
            )
        })
        list.add(
            HomeItem(
                4,
                "Clock"
            ) { openActivity(ClockActivity::class.java) })
        list.add(HomeItem(5, "ViewPager2") {
            openActivity(
                ViewPager2Activity::class.java
            )
        })
        list.add(HomeItem(6, "悬浮置顶1") {
            openActivity(
                StickyTop1Activity::class.java
            )
        })
        list.add(HomeItem(7, "悬浮置顶2") {
            openActivity(
                StickyTop2Activity::class.java
            )
        })
        list.add(
            HomeItem(
                8,
                "测试"
            ) { openActivity(TestActivity::class.java) })
        list.add(HomeItem(9, "IP") {
            Log.i("测试TAG", SysUtil.getLocalIpAddress())
        })
        list.add(HomeItem(10, "HookActivity") {
            openActivity(HookActivity::class.java)
        })

        adapter.submitList(list, RecyclerSubmit(0, 10, list.size))
    }

}
