package com.dev.zhaoys.ui.home

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.zhaoys.base.BaseActivity
import com.dev.zhaoys.extend.openActivity
import com.dev.zhaoys.ui.other.ClockActivity
import com.dev.zhaoys.ui.other.RulerActivity
import com.dev.zhaoys.ui.other.TestImageActivity
import com.dev.zhaoys.ui.other.UCHomeActivity
import com.dev.zhaoys.ui.qmhome.QmHomeActivity
import com.dev.zhaoys.ui.sticky.StickyRecyclerViewActivity
import com.dev.zhaoys.ui.testview.TestViewActivity
import com.dev.zhaoys.ui.wanandroid.WanMainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerAdapter
import com.zys.common.adapter.RecyclerSubmit
import com.zys.common.adapter.RecyclerSupport
import com.zys.common.imageload.ImageLoader
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.toast
import org.json.JSONArray

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
        support.onClickCallback = { position: Int, _: Int ->
            var id = adapter.currentList()[position].itemId()
            when (id.toInt()) {
                0 -> openActivity(WanMainActivity::class.java)
                1 -> openActivity(UCHomeActivity::class.java)
                2 -> openActivity(TestImageActivity::class.java)
                3 -> openActivity(StickyRecyclerViewActivity::class.java)
                4 -> openActivity(ClockActivity::class.java)
                5 -> openActivity(TestViewActivity::class.java)
                6 -> openActivity(RulerActivity::class.java)
                7 -> openActivity(QmHomeActivity::class.java)
                8 -> {
                    test()
                }
                else -> {
                    toast("未指定页面")
                }
            }
        }
        support.imageLoader = ImageLoader(this)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = RecyclerAdapter(support)
        recyclerView.adapter = adapter
        val list = mutableListOf<ItemCell>()
        list.add(HomeItem(0, "WanAndroid"))
        list.add(HomeItem(1, "仿UC首页"))
        list.add(HomeItem(2, "图片顶部裁剪"))
        list.add(HomeItem(3, "StickyRecycler"))
        list.add(HomeItem(4, "Clock"))
        list.add(HomeItem(5, "TestView"))
        list.add(HomeItem(6, "Ruler-View"))
        list.add(HomeItem(7, "QMUI-Home"))
        list.add(HomeItem(8, "测试"))
        adapter.submitList(list, RecyclerSubmit(0, 10, list.size))
    }

    private val gson = Gson()
    private fun test() {
        val json = "[abc,eee,dfs]"
        val b = isJsonArray(json)
        if (b) {
            val arr =
                gson.fromJson<Array<String>>(json, object : TypeToken<Array<String>>() {}.type)

            val s = StringBuilder()
            arr.forEach { a -> s.append("$a  ") }
            Log.i("测试TAG", s.toString())
        } else {
            Log.i("测试TAG", b.toString())
        }


    }

    /**
     * 判断字符串是否可以转化为JSON数组
     * @param content
     * @return
     */
    private fun isJsonArray(content: String): Boolean {
        if (content.isBlank())
            return false
        return try {
            JSONArray(content)
            true
        } catch (e: Exception) {
            false
        }

    }
}
