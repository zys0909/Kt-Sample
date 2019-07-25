package com.dev.zhaoys.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dev.zhaoys.base.BaseAdapter
import java.util.*

class MainAdapter(private val support: HomeSupport) : BaseAdapter<MainVisitable, HomeWrapper.BaseMainViewHolder>() {

    fun loadModule(temp: MainVisitable) {
        val newList = mutableListOf(temp)
        newList.addAll(0, list)
        apply(newList)
    }

    fun loadModules(temp: List<MainVisitable>) {
        val newList = mutableListOf<MainVisitable>()
        newList.addAll(list)
        newList.addAll(temp)
        apply(newList)
    }

    fun refresh(temp: List<MainVisitable>, isRefresh: Boolean = false) {
        val newList = mutableListOf<MainVisitable>()
        if (!isRefresh) {
            newList.addAll(list)
        }
        newList.addAll(temp)
        apply(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeWrapper.BaseMainViewHolder =
        support.createViewHolder(viewType, inflateView(parent, viewType))

    override fun onBindViewHolder(holder: HomeWrapper.BaseMainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemViewType(position: Int): Int = list[position].layoutId()

    private fun apply(temp: MutableList<MainVisitable>) {
        temp.sortWith(Comparator { o1, o2 ->
            o1.position().compareTo(o2.position())
        })
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                list[oldItemPosition].uid() == temp[newItemPosition].uid()

            override fun getOldListSize(): Int = list.size
            override fun getNewListSize(): Int = temp.size
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                list[oldItemPosition].uid() == temp[newItemPosition].uid()
        }, false)
        diffResult.dispatchUpdatesTo(this)
        list.clear()
        list.addAll(temp)
    }
}