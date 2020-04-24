package com.dev.zhaoys.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.zhaoys.base.BaseActivity
import com.dev.zhaoys.extend.openActivity
import com.dev.zhaoys.ui.other.*
import com.dev.zhaoys.ui.qmhome.StickyTop2Activity
import com.dev.zhaoys.ui.sticky.StickyTop1Activity
import com.dev.zhaoys.ui.wanandroid.WanMainActivity
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerAdapter
import com.zys.common.adapter.RecyclerSubmit
import com.zys.common.adapter.RecyclerSupport
import com.zys.common.imageload.ImageLoader
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.toast

/**
 * 描述:首页
 *
 * author zys
 */
class HomeActivity : BaseActivity() {
    private lateinit var adapter: RecyclerAdapter

    override fun layoutId(): Int = com.dev.zhaoys.R.layout.activity_home

    override fun init(savedInstanceState: Bundle?) {
        initToolbar("Home", false)
        val support = RecyclerSupport()
        support.imageLoader = ImageLoader(this)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = RecyclerAdapter(support)
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = true
        val list = mutableListOf<ItemCell>()
        list.add(HomeItem(0, "WanAndroid"))
        list.add(HomeItem(1, "仿UC首页"))
        list.add(HomeItem(2, "图片裁剪"))
        list.add(HomeItem(3, "Ruler-View"))
        list.add(HomeItem(4, "Clock"))
        list.add(HomeItem(5, "ViewPager2"))
        list.add(HomeItem(6, "悬浮置顶1"))
        list.add(HomeItem(7, "悬浮置顶2"))
        list.add(HomeItem(8, "测试"))
        adapter.submitList(list, RecyclerSubmit(0, 10, list.size))
        support.onClickCallback = { position: Int, _: Int ->
            val id = adapter.currentList()[position].itemId()
            when (id.toInt()) {
                0 -> openActivity(WanMainActivity::class.java)
                1 -> openActivity(UCHomeActivity::class.java)
                2 -> openActivity(ClipImageActivity::class.java)
                3 -> openActivity(RulerActivity::class.java)
                4 -> openActivity(ClockActivity::class.java)
                5 -> openActivity(ViewPager2Activity::class.java)
                6 -> openActivity(StickyTop1Activity::class.java)
                7 -> openActivity(StickyTop2Activity::class.java)
                8 -> openActivity(TestActivity::class.java)
                else -> {
                    toast("未指定页面")
                }
            }
        }
    }

}
