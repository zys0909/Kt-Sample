package com.group.dev.main

import android.util.Log
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.zhaoys.extend.openActivity
import com.group.dev.ui.flextag.FlexActivity
import com.dev.zhaoys.ui.hook.HookActivity
import com.dev.zhaoys.ui.other.*
import com.dev.zhaoys.ui.qmhome.StickyTop2Activity
import com.dev.zhaoys.ui.sticky.StickyTop1Activity
import com.dev.zhaoys.ui.wanandroid.WanMainActivity
import com.dev.zhaoys.utils.SysUtil
import com.group.dev.ui.decoration_sticky.DecorationStickyActivity
import com.group.dev.ui.fish.FishActivity
import com.group.dev.ui.tantan.TanTanActivity
import com.zys.base.BaseSampleFragment
import com.zys.common.adapter.ItemCell
import com.zys.core.ExtraConst
import com.zys.ext.toast

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/14
 */
class HomeFragment : BaseSampleFragment() {

    override val titleBar: String = "Home"

    override val recyclerManager: RecyclerView.LayoutManager
        get() = GridLayoutManager(context, 2)

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
        list.add(MainCell("ViewPager2") {
            openActivity<ViewPager2Activity>()
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
            openActivity<TextActivity>()
        })
        list.add(MainCell("流式布局") {
            openActivity<FlexActivity>()
        })
        list.add(MainCell("HookActivity") {
            openActivity<HookActivity>()
        })
        list.add(MainCell("分组-悬浮") {
            openActivity<DecorationStickyActivity>(bundleOf(ExtraConst.Pager_Title to it))
        })
        list.add(MainCell("仿探探卡片") {
            openActivity<TanTanActivity>(bundleOf(ExtraConst.Pager_Title to it))
        })
        list.add(MainCell("灵动的锦鲤") {
            openActivity<FishActivity>(bundleOf(ExtraConst.Pager_Title to it))
        })
        return list
    }
}