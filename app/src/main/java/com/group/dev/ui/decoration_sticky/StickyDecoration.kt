package com.group.dev.ui.decoration_sticky

import android.graphics.*
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

    private val groupHeaderHeight = 30.dp
    private val dividerHeight = 2.dp
    private val paddingLeft = 10.dpf
    private val paint by lazy { Paint() }
    private val rect = Rect()
    private val path = Path()

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
                    val groupName = adapter.getGroupName(position)
                    val top = (view.top - groupHeaderHeight).toFloat()
                    drawHeader(c, groupName, left, top, right, view.top.toFloat(), false)
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
            val groupHeader = adapter.isGroupHeader(position + 1)

            val bottom = if (groupHeader) {
                top + kotlin.math.min(groupHeaderHeight, view.bottom - parent.paddingTop)
            } else {
                top + groupHeaderHeight
            }
            val groupName = adapter.getGroupName(position)
            drawHeader(c, groupName, left, top, right, bottom, true)
        }
    }

    private fun drawHeader(
        c: Canvas, groupName: String,
        left: Float, top: Float, right: Float, bottom: Float,
        isOver: Boolean
    ) {
        c.save()
        paint.color = 0xFFEEEEEE.toInt()
        c.drawRect(left, top, right, bottom, paint)
        c.restore()

        paint.getTextBounds(groupName, 0, groupName.length, rect)
        val textRight = (left + right) / 2
        if (isOver) {
            c.clipRect(left, top, textRight, bottom)
        }
        c.save()
        paint.color = Color.RED
        path.reset()
        path.moveTo(left, bottom - groupHeaderHeight)
        path.lineTo(textRight, bottom - groupHeaderHeight)
        path.lineTo(bottom - groupHeaderHeight / 2f, textRight - paddingLeft)
        path.lineTo(textRight, bottom)
        path.lineTo(left, bottom)
        c.drawPath(path, paint)


        paint.color = Color.WHITE
        val y = bottom - groupHeaderHeight / 2 + rect.height() / 2
        c.drawText(groupName, left + paddingLeft, y, paint)
        c.restore()
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