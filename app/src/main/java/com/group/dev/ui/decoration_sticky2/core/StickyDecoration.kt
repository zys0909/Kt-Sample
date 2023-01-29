package com.group.dev.ui.decoration_sticky2.core

import android.graphics.Canvas
import android.graphics.Rect
import android.text.TextUtils
import android.util.LruCache
import android.util.SparseArray
import android.view.MotionEvent
import android.view.SoundEffectConstants
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.group.common.ext.dp

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
class StickyDecoration(val recyclerView: RecyclerView) : RecyclerView.ItemDecoration() {

    private val viewCache = LruCache<String, AbstractHeaderHolder>(16)
    private val headerRect = SparseArray<Rect>()
    private val viewRect = Rect()

    private val dividerHeight = 2.dp

    private var clickId = 0

    init {
        recyclerView.addOnItemTouchListener(StickyRecyclerHeadersTouchListener(recyclerView, this))
    }

    fun setClickId(@IdRes id: Int) {
        clickId = id
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        headerRect.clear()
        val adapter = parent.adapter
        if (adapter is ISticky) {
            val childCount = parent.childCount
            for (layoutPosition in 0 until childCount) {
                val childAt = parent.getChildAt(layoutPosition)

                val adapterPosition = parent.getChildAdapterPosition(childAt)

                if (adapterPosition != RecyclerView.NO_POSITION
                    && (layoutPosition == 0 || adapter.isGroupHeader(adapterPosition))
                ) {
                    val headerView = getHeaderView(parent, adapter, adapterPosition)
                    c.save()
                    val left = childAt.left
                    val top = getHeaderTop(
                        parent,
                        childAt,
                        headerView,
                        adapter,
                        adapterPosition,
                        layoutPosition
                    )
                    c.translate(left.toFloat(), top.toFloat())
                    headerView.draw(c)
                    c.restore()
                    headerRect.put(
                        adapterPosition,
                        Rect(left, top, left + headerView.width, top + headerView.height)
                    )
                }
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val adapter = parent.adapter
        if (adapter is ISticky) {
            val position = parent.getChildLayoutPosition(view)
            var headerHeight = 0
            //判断这个位置是否有分组的头部
            if (position != RecyclerView.NO_POSITION && adapter.isGroupHeader(position)) {
                val headerView = getHeaderView(parent, adapter, position)
                headerHeight = headerView.height
            }
            outRect.top = dividerHeight + headerHeight
        }
    }

    fun getHeaderView(parent: RecyclerView, iSticky: ISticky, position: Int): View {
        val groupId = iSticky.getGroupId(position)
        var abstractHolder: AbstractHeaderHolder? = viewCache[groupId]
        if (abstractHolder == null) {
            abstractHolder = iSticky.onCreateHeaderViewHolder(parent)
            viewCache.put(groupId, abstractHolder)
        }
        val headerView = abstractHolder.itemView
        iSticky.onBindHeaderViewHolder(
            abstractHolder, position, isFirstItem(recyclerView, position)
        )

        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.EXACTLY)

        val childWidth = ViewGroup.getChildMeasureSpec(
            widthSpec, parent.paddingLeft + parent.paddingRight,
            headerView.layoutParams.width
        )
        val childHeight = ViewGroup.getChildMeasureSpec(
            heightSpec, parent.paddingTop + parent.paddingBottom,
            headerView.layoutParams.height
        )
        headerView.measure(childWidth, childHeight)
        headerView.layout(0, 0, headerView.measuredWidth, headerView.measuredHeight)
        return headerView
    }

    /**
     * position位置 是否屏幕上第一个View
     */
    private fun isFirstItem(parent: RecyclerView, position: Int): Boolean {
        if (position == 0) {
            return true
        }
        if (recyclerView.childCount == 0) {
            return false
        }
        return parent.getChildAdapterPosition(parent.getChildAt(0)) == position
    }

    /**
     * 获取headerView 顶部偏移量
     */
    private fun getHeaderTop(
        parent: RecyclerView,
        child: View,
        headerView: View,
        iSticky: ISticky,
        adapterPosition: Int,
        layoutPosition: Int
    ): Int {
        val headerHeight = headerView.height
        var top = child.y.toInt() - headerHeight
        if (layoutPosition == 0) {
            val childCount = parent.childCount
            val curGroupId = iSticky.getGroupId(adapterPosition)
            for (i in 1 until childCount) {
                val nextPosition = parent.getChildAdapterPosition(parent.getChildAt(i))
                if (nextPosition != RecyclerView.NO_POSITION) {
                    val nextGroupId = iSticky.getGroupId(nextPosition)
                    if (!TextUtils.equals(curGroupId, nextGroupId)) {
                        val nextChild = parent.getChildAt(i)
                        val nextHeaderHeight = getHeaderView(parent, iSticky, nextPosition).height
                        val offset = nextChild.y.toInt() - (headerHeight + nextHeaderHeight)
                        if (offset < 0) {
                            return offset
                        } else {
                            break
                        }
                    }
                }
            }
        }
        top = 0.coerceAtLeast(top)
        return top
    }


    /**
     * 处理点击事件
     */
    fun applyTouchEvent(e: MotionEvent, performClick: Boolean): Boolean {
        try {
            val position = findHeaderPositionUnder(e.x.toInt(), e.y.toInt())
            val adapter = recyclerView.adapter
            if (position != -1 && adapter is ISticky && clickId != 0) {
                val headerView = getHeaderView(recyclerView, adapter, position)
                val view = headerView.findViewById<View>(clickId)
                if (view.isClickable && view.visibility == View.VISIBLE
                    && findHeaderClickView(view, e.x.toInt(), e.y.toInt())
                ) {
                    if (performClick) {
                        view.performClick()
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        headerView.onTouchEvent(e)
                    }
                    return true
                }
            }
        } catch (e: Exception) {
        }
        return false
    }

    /**
     * 根据点击的坐标查找是不是点击在header的区域
     */
    private fun findHeaderPositionUnder(x: Int, y: Int): Int {
        val size = headerRect.size()
        for (i in 0 until size) {
            val rect = headerRect.valueAt(i)
            if (rect.contains(x, y)) {
                return headerRect.keyAt(i)
            }
        }
        return -1
    }

    /**
     * 判断是否在header需要响应点击事件的区域
     */
    private fun findHeaderClickView(view: View?, x: Int, y: Int): Boolean {
        if (view == null) {
            return false
        }
        val size = headerRect.size()
        for (i in 0 until size) {
            val rect = headerRect.valueAt(i)
            if (rect.contains(x, y)) {
                viewRect.setEmpty()
                val left = rect.left + view.left
                val top = rect.top + view.top
                viewRect.set(left, top, left + view.width, top + view.height)
                return viewRect.contains(x, y)
            }
        }
        return false
    }

}