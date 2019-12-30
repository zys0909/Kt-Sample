package com.dev.zhaoys.widget

import android.view.View
import androidx.annotation.Nullable
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs


/**
 * 描述:
 *
 * author zys
 * create by 2019-12-25
 */
class GalleryPagerSnapHelper : PagerSnapHelper() {

    companion object {
        const val STAY_SCALE = 0.85f
    }

    private var scale = STAY_SCALE

    override fun attachToRecyclerView(@Nullable recyclerView: RecyclerView?) {
        super.attachToRecyclerView(recyclerView)
        if (recyclerView == null) {
            return
        }
        recyclerView.clipToPadding = false
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager
                val snapView: View? = findSnapView(layoutManager)
                snapView?.let {
                    val position = recyclerView.getChildAdapterPosition(snapView)
                    val leftView: View? = layoutManager!!.findViewByPosition(position - 1)
                    val rightView: View? = layoutManager.findViewByPosition(position + 1)
                    calculateOffset(recyclerView, snapView)
                    calculateOffset(recyclerView, leftView)
                    calculateOffset(recyclerView, rightView)
                }
            }
        })
    }

    private fun calculateOffset(recyclerView: RecyclerView, view: View?) {
        if (view == null) return
        val layoutManager = recyclerView.layoutManager
        val isVertical = layoutManager!!.canScrollVertically()
        val viewStart: Int = if (isVertical) view.top else view.left
        val viewEnd: Int = if (isVertical) view.bottom else view.right
        val centerX = if (isVertical) recyclerView.height / 2 else recyclerView.width / 2
        val childCenter = (viewStart + viewEnd) / 2
        val distance = abs(childCenter - centerX)
        var value = scale
        if (distance <= centerX) {
            val offset = distance / centerX.toFloat()
            value = 1 - (1 - value) * offset
        }
        view.scaleX = value
        view.scaleY = value
    }
}