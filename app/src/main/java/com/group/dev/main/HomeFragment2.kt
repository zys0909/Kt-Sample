package com.group.dev.main

import android.view.View
import com.group.common.adapter.ItemCell
import com.group.common.base.BaseSampleFragment
import com.group.common.ext.openActivity
import com.group.dev.ui.wanandroid.WanMainActivity

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/26
 */
class HomeFragment2 : BaseSampleFragment() {
    override fun initList(): List<ItemCell> {
        val list = mutableListOf<MainCell>()
        list.add(MainCell("WanAndroid") {
            openActivity<WanMainActivity>()
        })
        return list
    }

    override fun init(view: View) {
        setTitle("BBBB", false)
    }
}