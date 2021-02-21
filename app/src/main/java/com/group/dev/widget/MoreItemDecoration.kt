package com.group.dev.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group.common.ext.dp

/**
 * 描述:
 *
 * author zys
 * create by 2020/8/6
 */
class MoreItemDecoration : RecyclerView.ItemDecoration() {

    private val spaceValue = 5.dp

    private val paint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.TRANSPARENT
            style = Paint.Style.FILL
        }
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (view.layoutParams is GridLayoutManager.LayoutParams) {
            val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
            val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
            val spanSize = layoutParams.spanSize
            val spanIndex = layoutParams.spanIndex
            when {
                spanSize == spanCount -> outRect.set(spaceValue, 0, 0, 0)
                spanIndex == 0 -> outRect.set(spaceValue, spaceValue, spaceValue, spaceValue)
                spanIndex + 1 == spanCount -> outRect.set(
                    spaceValue,
                    spaceValue,
                    spaceValue,
                    spaceValue
                )
                else -> outRect.set(spaceValue, spaceValue, spaceValue, spaceValue)
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childAt = parent.getChildAt(i)
            if (childAt.layoutParams is GridLayoutManager.LayoutParams) {
                val layoutParams = childAt.layoutParams as GridLayoutManager.LayoutParams
                val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
                val spanSize = layoutParams.spanSize
                val spanIndex = layoutParams.spanIndex
                when {
                    spanSize == spanCount -> {

                    }
                    spanIndex == 0 -> {

                    }
                    spanIndex + 1 == spanCount -> {
                    }
                    else -> {

                    }
                }
            }
        }
    }
}