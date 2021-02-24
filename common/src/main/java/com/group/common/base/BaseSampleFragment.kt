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
import com.group.common.ext.fitSystemBar
import com.group.common.widget.AppToolBar

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/14
 */
abstract class BaseSampleFragment : Fragment() {

    protected val appToolBar by lazy { AppToolBar(requireContext()) }
    protected val recyclerView by lazy { RecyclerView(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = LinearLayout(requireContext())
        rootView.orientation = LinearLayout.VERTICAL
        recyclerView.setPadding(10.dp, 0, 10.dp, 0)
        recyclerView.layoutManager = recyclerManager
        recyclerView.adapter = RecyclerAdapter(support).also {
            it.submit(initList())
        }
        appToolBar.visibility = View.GONE
        rootView.addView(appToolBar)
        rootView.addView(recyclerView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    open val support: RecyclerSupport = RecyclerSupport()

    open val recyclerManager: RecyclerView.LayoutManager
        get() = GridLayoutManager(context, 3)

    abstract fun initList(): List<ItemCell>

    abstract fun init(view: View)

    fun setTitle(titleChar: CharSequence, homeAsUp: Boolean = true) {
        appToolBar.visibility = View.VISIBLE
        appToolBar.fitSystemBar()
        appToolBar.setTitle(titleChar, homeAsUp)
    }
}