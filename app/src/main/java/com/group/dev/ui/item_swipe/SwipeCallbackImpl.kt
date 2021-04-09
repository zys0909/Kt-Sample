package com.group.dev.ui.item_swipe

import android.animation.ObjectAnimator
import android.graphics.Canvas
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.group.common.ext.dp
import com.group.common.ext.dpf
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * 描述:RecyclerView 侧滑ItemTouchHelper.Callback 实现类
 *
 * author zys
 * create by 2021/3/16
 */
internal class SwipeCallbackImpl(val lifecycle: Lifecycle) : ItemTouchHelper.Callback() {

    var maxSwipeWidth: Int = 88.dp

    private val duration: Long
        get() = (maxSwipeWidth / 30.dpf * 30).toLong()

    private var curRecyclerView: RecyclerView? = null
    private var curPosition = -1
    private var currentScrollX = 0
    private var flag = false

    init {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_PAUSE) {
                    reset()
                }
            }
        })
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder
    ): Int {
        setupRecyclerView(recyclerView)
        val supportSwipe = when (viewHolder) {
            is ItemSwipe -> {
                if (viewHolder.maxSwipeWidth() > 0) {
                    maxSwipeWidth = viewHolder.maxSwipeWidth()
                }
                viewHolder.enable()
            }
            else -> true
        }
        val swipeFlag = if (supportSwipe) 12 else 0
        if (viewHolder.layoutPosition != curPosition) {
            reset()
            if (supportSwipe) {
                curPosition = viewHolder.layoutPosition
            }
        }
        return makeMovementFlags(0, swipeFlag)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {


    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE) {
            return
        }
        if (viewHolder.layoutPosition != curPosition) {
            return
        }
        val view = viewHolder.itemView
        if (dX == 0f) {
            currentScrollX = view.scrollX
        }
        if (isCurrentlyActive) {//手指滑动
            if (dX < 0) {
                view.scrollX = min((currentScrollX - dX).toInt(), maxSwipeWidth)
            } else if (dX > 0) {
                view.scrollX = max((currentScrollX - dX).toInt(), 0)
            }
        } else {    //手指离开后
            if (dX < 0) {
                scrollItemView(view, false, view.scrollX, maxSwipeWidth)
            } else if (dX > 0) {
                scrollItemView(view, true, view.scrollX, 0)
            }
        }
    }

    /**
     * 手里离开屏幕后的动画操作 完成后 调用
     */
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder.itemView.scrollX > maxSwipeWidth / 2) {
            viewHolder.itemView.scrollX = maxSwipeWidth
        } else if (viewHolder.itemView.scrollX < 0) {
            viewHolder.itemView.scrollX = 0
        }
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return Float.MAX_VALUE
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return Float.MAX_VALUE
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        if (this.curRecyclerView == null) {
            this.curRecyclerView = recyclerView
        }
        if (flag) {
            return
        }
        flag = true
        curRecyclerView?.addOnItemTouchListener(
            ItemTouchListener(recyclerView) { _: View, _: Int -> reset() }
        )
        curRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                reset()
            }
        })
    }

    private fun scrollItemView(view: View, isReset: Boolean = true, start: Int, end: Int) {
        val duration = abs(currentScrollX - end) * 1f / maxSwipeWidth * duration
        if (isReset) {
            curPosition = -1
        }
        ObjectAnimator.ofInt(view, "ScrollX", start, end)
            .setDuration(duration.toLong())
            .start()
    }

    /**
     * 重置到初始状态
     */
    private fun reset() {
        val view = this.curRecyclerView?.findViewHolderForLayoutPosition(curPosition)?.itemView
        view ?: return
        curPosition = -1
        ObjectAnimator.ofInt(view, "ScrollX", currentScrollX, 0)
            .setDuration(duration)
            .start()
    }
}