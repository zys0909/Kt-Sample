package com.dev.zhaoys.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * BaseAdapter
 */
abstract class BaseAdapter<E, VH : RecyclerView.ViewHolder>() :
    RecyclerView.Adapter<VH>() {
    val list = ArrayList<E>()

    override fun getItemCount(): Int = list.size

    protected fun inflateView(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context).inflate(viewType, parent, false)!!

}