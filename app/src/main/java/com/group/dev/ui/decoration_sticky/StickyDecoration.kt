package com.group.dev.ui.decoration_sticky

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.zhaoys.extend.dp
import com.dev.zhaoys.extend.dpf

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
class StickyDecoration : RecyclerView.ItemDecoration() {

    private val groupHeaderHeight = 50.dp
    private val dividerHeight = 2.dp
    private val paint by lazy { Paint() }
    private val rect = Rect()

    init {
        paint.run {
            textSize = 20.dpf
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter
        if (adapter is ISticky) {
            val count = parent.childCount
            val left = parent.paddingLeft.toFloat()
            val right = (parent.width - parent.paddingRight).toFloat()
            for (i in 0..count) {
                val view = parent.getChildAt(i) ?: break
                val position = parent.getChildLayoutPosition(view)

                if (view.top - groupHeaderHeight < parent.paddingTop) {
                    continue
                }

                if (adapter.isGroupHeader(position)) {
                    paint.color = Color.RED
                    c.drawRect(
                        left,
                        (view.top - groupHeaderHeight).toFloat(),
                        right,
                        view.top.toFloat(), paint
                    )
                    paint.color = Color.WHITE
                    val groupName = adapter.getGroupName(position)
                    paint.getTextBounds(groupName, 0, groupName.length, rect)

                    c.drawText(
                        groupName,
                        left + 20.dp,
                        (view.top - groupHeaderHeight / 2 + rect.height() / 2).toFloat(),
                        paint
                    )

                } else {
                    paint.color = Color.BLACK
                    c.drawRect(
                        left,
                        (view.top - dividerHeight).toFloat(),
                        right,
                        view.top.toFloat(),
                        paint
                    )
                }
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter
        if (adapter is ISticky) {
            // 返回可见区域内的第一个item的position
            val position =
                (parent.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                    ?: return
            val view = parent.findViewHolderForAdapterPosition(position)?.itemView ?: return
            val left = parent.paddingLeft.toFloat()
            val right = (parent.width - parent.paddingRight).toFloat()
            val top = parent.paddingTop.toFloat()
            // 当第二个是组的头部的时候
            if (adapter.isGroupHeader(position + 1)) {
                val height = kotlin.math.min(groupHeaderHeight, view.bottom - parent.paddingTop)

                paint.color = Color.RED
                c.drawRect(
                    left,
                    top,
                    right,
                    top + height, paint
                )
                paint.color = Color.WHITE
                val groupName = adapter.getGroupName(position)
                paint.getTextBounds(groupName, 0, groupName.length, rect)
                c.clipRect(left, top, right, top + height)
                c.drawText(
                    groupName,
                    left + 20.dp,
                    (top + height - groupHeaderHeight / 2 + rect.height() / 2),
                    paint
                )

            } else {
                paint.color = Color.RED
                c.drawRect(
                    left,
                    top,
                    right,
                    top + groupHeaderHeight, paint
                )
                paint.color = Color.WHITE
                val groupName = adapter.getGroupName(position)
                paint.getTextBounds(groupName, 0, groupName.length, rect)
                c.drawText(
                    groupName,
                    left + 20.dp,
                    (top + groupHeaderHeight / 2 + rect.height() / 2),
                    paint
                )
            }


        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapter = parent.adapter
        if (adapter is ISticky) {
            val position = parent.getChildLayoutPosition(view)
            if (adapter.isGroupHeader(position)) {
                outRect.top = groupHeaderHeight
            } else {
                outRect.top = dividerHeight
            }
        }
    }
}