package com.dev.zhaoys.extend

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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


