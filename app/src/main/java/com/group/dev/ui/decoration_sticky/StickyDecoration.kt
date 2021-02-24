package com.group.dev.ui.decoration_sticky

import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group.common.ext.dp
import com.group.common.ext.dpf

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
class StickyDecoration(@ColorInt private val color: Int = 0) : RecyclerView.ItemDecoration() {

    private val groupHeaderHeight = 50.dp
    private val dividerHeight = 2.dp
    private val paddingLeft = 10.dpf
    private val paint by lazy { Paint() }
    private val rect = RectF()
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

                //处理paddingTop
                if (view.top - groupHeaderHeight < parent.paddingTop) {
                    continue
                }
                //画分组
                if (adapter.isGroupHeader(position)) {
                    val groupName = adapter.getGroupName(position)
                    val top = (view.top - groupHeaderHeight - dividerHeight).toFloat()
                    rect.set(left, top, right, view.top.toFloat())
                    drawHeader(c, groupName, rect, parent, false)
                }
                //画分隔线
                paint.color = Color.BLACK
                val dividerTop = view.top.toFloat()
                c.drawRect(left, dividerTop, right, dividerTop - dividerHeight, paint)
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
            rect.set(left, top, right, bottom)
            drawHeader(c, groupName, rect, parent, true)
        }
    }

    private fun drawHeader(
        c: Canvas, groupName: String, rectF: RectF,
        parent: RecyclerView, isOver: Boolean
    ) {
        if (isOver) {
            c.clipRect(rectF)
        }
        c.save()
        //画背景
        paint.color = if (color == 0) {
            (parent.background as? ColorDrawable)?.color ?: 0
        } else color
        c.drawRect(rectF, paint)
        c.restore()
        rectF.inset(10.dpf, 10.dpf)

        val height = groupHeaderHeight - 20.dp
        val textWidth = paint.measureText(groupName, 0, groupName.length)
        c.save()
        //画凹箭头
        paint.color = Color.RED
        val textRight = rectF.left + paddingLeft * 2 + textWidth
        val centerX = textRight + (height * 2 / 3f)
        val centerY = rectF.bottom - height / 2f

        path.reset()
        path.moveTo(rectF.left, rectF.bottom - height)
        path.lineTo(centerX, rectF.bottom - height)
        path.lineTo(textRight, centerY)
        path.lineTo(centerX, rectF.bottom)
        path.lineTo(rectF.left, rectF.bottom)
        c.drawPath(path, paint)

        //画文字
        paint.color = Color.WHITE
        val fm = paint.fontMetrics
        val y = centerY - (fm.ascent + fm.descent) / 2
        c.drawText(groupName, rectF.left + paddingLeft, y, paint)
        c.restore()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val adapter = parent.adapter
        if (adapter is ISticky) {
            val position = parent.getChildLayoutPosition(view)
            if (adapter.isGroupHeader(position)) {
                outRect.top = groupHeaderHeight + dividerHeight
            } else {
                outRect.top = dividerHeight
            }
        }
    }
}