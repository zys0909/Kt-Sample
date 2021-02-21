package com.group.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group.common.adapter.ItemCell
import com.group.common.adapter.RecyclerAdapter
import com.group.common.adapter.RecyclerSupport
import com.group.common.ext.dp
import com.group.common.widget.AppToolBar

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/14
 */
abstract class BaseSampleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = RecyclerView(requireContext()).apply {
            setPadding(10.dp, 0, 10.dp, 0)
            layoutManager = recyclerManager
            adapter = RecyclerAdapter(support).also {
                it.submit(initList())
            }
        }
        return LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            if (titleBar.isNotEmpty()) {
                addView(AppToolBar(context).apply {
                    setBackgroundColor(0xFFEEEEEE.toInt())
                    title = titleBar
                })
            }
            addView(recyclerView)
        }
    }

    open val support: RecyclerSupport = RecyclerSupport()

    open val titleBar: String = ""

    open val recyclerManager: RecyclerView.LayoutManager
        get() = GridLayoutManager(context, 3)

    abstract fun initList(): List<ItemCell>
}