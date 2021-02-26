package com.group.dev.main

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group.common.adapter.ItemCell
import com.group.common.base.BaseSampleFragment
import com.group.common.ext.openActivity
import com.group.common.ext.toast
import com.group.dev.ui.clock.ClockActivity
import com.group.dev.ui.decoration_sticky.DecorationStickyActivity
import com.group.dev.ui.fish.FishActivity
import com.group.dev.ui.flextag.FlexActivity
import com.group.dev.ui.hook.HookActivity
import com.group.dev.ui.other.*
import com.group.dev.ui.qmhome.StickyTop2Activity
import com.group.dev.ui.sticky.StickyTop1Activity
import com.group.dev.ui.tantan.TanTanActivity
import com.group.dev.ui.wanandroid.WanMainActivity
import com.group.dev.utils.SysUtil

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/14
 */
class HomeFragment1 : BaseSampleFragment() {


    override val recyclerManager: RecyclerView.LayoutManager
        get() = GridLayoutManager(context, 2)

    override fun init(view: View) {
        setTitle("Home", false)
    }

    override fun initList(): List<ItemCell> {
        val list = mutableListOf<ItemCell>()
        list.add(MainCell("WanAndroid") {
            openActivity<WanMainActivity>()
        })
        list.add(MainCell("仿UC首页") {
            openActivity<UCHomeActivity>()
        })
        list.add(MainCell("图片裁剪") {
            openActivity<ClipImageActivity>()
        })
        list.add(MainCell("Ruler-View") {
            openActivity<RulerActivity>()
        })
        list.add(MainCell("Clock") {
            openActivity<ClockActivity>()
        })

        list.add(MainCell("悬浮置顶1") {
            openActivity<StickyTop1Activity>()
        })
        list.add(MainCell("悬浮置顶2") {
            openActivity<StickyTop2Activity>()
        })
        list.add(MainCell("测试") {
            openActivity<TestActivity>()
        })
        list.add(MainCell("IP") {
            Log.i("测试TAG", SysUtil.getLocalIpAddress())
            toast(SysUtil.getLocalIpAddress())
        })
        list.add(MainCell("Text测试") {
            openActivity<TextActivity>(it)
        })
        list.add(MainCell("流式布局") {
            openActivity<FlexActivity>(it)
        })
        list.add(MainCell("HookActivity") {
            openActivity<HookActivity>(it)
        })
        list.add(MainCell("分组-悬浮") {
            openActivity<DecorationStickyActivity>(it)
        })
        list.add(MainCell("仿探探卡片") {
            openActivity<TanTanActivity>(it)
        })
        list.add(MainCell("灵动的锦鲤") {
            openActivity<FishActivity>(it)
        })
        return list
    }
}