package com.dev.zhaoys.ui.other

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.zhaoys.base.BaseActivity
import com.dev.zhaoys.ui.home.HomeItem
import com.dev.zhaoys.utils.StrUtil
import com.google.android.material.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerAdapter
import com.zys.common.adapter.RecyclerSubmit
import com.zys.common.adapter.RecyclerSupport
import com.zys.common.imageload.ImageLoader
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.toast
import java.text.DecimalFormat

/**
 * 描述:
 *
 * author zys
 * create by 2019-12-30
 */
class TestActivity : BaseActivity() {
    private lateinit var adapter: RecyclerAdapter
    override fun layoutId(): Int = com.dev.zhaoys.R.layout.activity_home

    override fun init(savedInstanceState: Bundle?) {
        initToolbar("Home", false)
        val support = RecyclerSupport()
        support.imageLoader = ImageLoader(this)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = RecyclerAdapter(support)
        recyclerView.adapter = adapter
        val list = mutableListOf<ItemCell>()
        list.add(HomeItem(0, "test1"))
        list.add(HomeItem(1, "test2"))
        list.add(HomeItem(2, "test3"))
        list.add(HomeItem(3, "test4"))
        list.add(HomeItem(4, "test5"))
        list.add(HomeItem(5, "test6"))
        list.add(HomeItem(6, "test7"))
        list.add(HomeItem(7, "test8"))
        list.add(HomeItem(8, "test9"))
        adapter.submitList(list, RecyclerSubmit(0, 10, list.size))
        support.onClickCallback = { position: Int, _: Int ->
            val id = adapter.currentList()[position].itemId()
            when (id.toInt()) {
                0 -> {
                    test()
                }
                1 -> {
                    val a = Double.MAX_VALUE
                    val d = DecimalFormat("#.0")
                    val toDouble = d.format(a).toDouble()
                    Log.i("测试TAG", toDouble.toString())
                }
                2 -> {
                }
                3 -> {
                }
                4 -> {
                }
                5 -> {
                }
                6 -> {
                }
                7 -> {
                }
                8 -> {
                }
                else -> {
                    toast("I do not")
                }
            }
        }
    }

    private fun t1() {

    }
    private fun getThemeResId(context: Context, themeId: Int): Int {
        var themeId = themeId
        if (themeId == 0) { // If the provided theme is 0, then retrieve the dialogTheme from our theme
            val outValue = TypedValue()
            themeId = if (context.theme.resolveAttribute(
                    R.attr.bottomSheetDialogTheme,
                    outValue,
                    true
                )
            ) {
                outValue.resourceId
            } else { // bottomSheetDialogTheme is not provided; we default to our light theme
                R.style.Theme_Design_Light_BottomSheetDialog
            }
        }
        return themeId
    }

    private val gson = Gson()
    private fun test() {
        val json = "[abc,eee,dfs]"
        val b = StrUtil.isJsonArray(json)
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
}