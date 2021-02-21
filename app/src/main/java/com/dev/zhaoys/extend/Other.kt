package com.dev.zhaoys.extend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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


