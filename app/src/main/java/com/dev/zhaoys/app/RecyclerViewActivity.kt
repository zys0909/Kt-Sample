package com.dev.zhaoys.app

import androidx.recyclerview.widget.RecyclerView

/**
 * 描述:简单的RecycleView 基类
 *
 * author zys
 * create by 2021/2/21
 */
abstract class RecyclerViewActivity : SimpleViewActivity<RecyclerView>() {

    override fun generateRootView(): RecyclerView {
        return RecyclerView(this)
    }

}