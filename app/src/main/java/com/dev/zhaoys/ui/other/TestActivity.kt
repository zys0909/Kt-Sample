package com.dev.zhaoys.ui.other

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.zhaoys.base.BaseActivity
import com.dev.zhaoys.extend.toast
import com.dev.zhaoys.HomeItem
import com.dev.zhaoys.ui.proxy.DynamicJava
import com.dev.zhaoys.ui.proxy.DynamicTest
import com.dev.zhaoys.ui.proxy.IBuy
import com.zys.common.adapter.ItemCell
import com.zys.common.adapter.RecyclerAdapter
import com.zys.common.adapter.RecyclerSubmit
import com.zys.common.adapter.RecyclerSupport
import com.zys.common.imageload.ImageLoader
import kotlinx.android.synthetic.main.activity_sample_list.*
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.text.DecimalFormat

/**
 * 描述:
 *
 * author zys
 * create by 2019-12-30
 */
class TestActivity : BaseActivity() {
    private lateinit var adapter: RecyclerAdapter
    override fun layoutId(): Int = com.dev.zhaoys.R.layout.activity_sample_list

    val cha =
        arrayOf(
            "🎵",
            "♪",
            "♩",
            "♫",
            "♬",
            "¶",
            "‖",
            "♭",
            "♯",
            "§",
            "∮",
            "※",
            "∴",
            "∵",
            "∽",
            "¥",
            "Ψ",
            "$"
        )

    override fun init(savedInstanceState: Bundle?) {
        initToolbar("测试页面", false)
        val support = RecyclerSupport()
        support.imageLoader = ImageLoader(this)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = RecyclerAdapter(support)
        recyclerView.adapter = adapter
        val list = mutableListOf<ItemCell>()
        list.add(HomeItem(0))
        list.add(HomeItem(1))
        list.add(HomeItem(2))
        list.add(HomeItem(3))
        list.add(HomeItem(4))
        list.add(HomeItem(5))
        list.add(HomeItem(6))
        list.add(HomeItem(7))
        list.add(HomeItem(8))
        adapter.submitList(list, RecyclerSubmit(0, 10, list.size))
        support.onClickCallback = { position: Int, _: Int ->
            val id = adapter.currentList()[position].itemId()
            when (id.toInt()) {
                0 -> {

                }
                1 -> {
                    val a = Double.MAX_VALUE
                    val d = DecimalFormat("#.0")
                    val toDouble = d.format(a).toDouble()
                    Log.i("测试TAG", toDouble.toString())
                }
                2 -> {
                    for (str in cha) {
                        Log.i("测试TAG", "str = $str, length = ${str.length}")
                    }
                }
                3 -> {
                    DynamicTest.t1()
                }
                4 -> {
                    test3(IBuy::class.java).buyTicket("IBuy", -2)
                }
                5 -> {
                    DynamicJava.t()
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

    @Suppress("unchecked")
    private fun <T> test3(clazz: Class<T>): T {
        val instance = Proxy.newProxyInstance(
            clazz.classLoader, arrayOf(clazz), object : InvocationHandler {
                override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
                    Log.i("测试TAG", "before invoke")
                    val invoke = method?.invoke(clazz, args)
                    Log.i("测试TAG", "after invoke")
                    return invoke
                }
            }
        )
        Log.i("测试TAG", instance.toString())
        return instance as T

    }
}

