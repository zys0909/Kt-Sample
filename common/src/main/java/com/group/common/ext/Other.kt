package com.group.common.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/7/26 0026
 */
var RecyclerView.orientation: Int
    set(value) {
        this.layoutManager = LinearLayoutManager(this.context, value, false)
    }
    get() = (this.layoutManager as LinearLayoutManager).orientation

val RecyclerView.LayoutManager.spanCount: Int
    get() = when (this) {
        is GridLayoutManager -> this.spanCount
        is StaggeredGridLayoutManager -> this.spanCount
        else -> throw IllegalArgumentException("layoutManager must be GridLayoutManager or StaggeredGridLayoutManager")
    }

inline fun <reified T> AppCompatActivity.openActivity(bundle: Bundle = bundleOf()) {
    this.startActivity(Intent(this, T::class.java).putExtras(bundle))
//    this.overridePendingTransition(0, 0)
}

inline fun <reified T> Fragment.openActivity(bundle: Bundle = bundleOf()) {

    this.startActivity(Intent(requireContext(), T::class.java).putExtras(bundle))
//    requireActivity().overridePendingTransition(0, 0)
}

internal fun Context.toast(msg: String) {
    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
}


