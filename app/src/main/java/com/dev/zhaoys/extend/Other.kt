package com.dev.zhaoys.extend

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.internals.AnkoInternals.createIntent

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/7/26 0026
 */
internal var RecyclerView.orientation: Int
    set(value) {
        this.layoutManager = LinearLayoutManager(this.context, value, false)
    }
    get() = (this.layoutManager as LinearLayoutManager).orientation

internal fun <T> AppCompatActivity.openActivity(
    clazz: Class<T>,
    params: Array<out Pair<String, Any?>> = arrayOf()
) {
    this.startActivity(createIntent(this, clazz, params))
    this.overridePendingTransition(0,0)
}


