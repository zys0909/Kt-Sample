package com.group.dev.ui.item_swipe

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

/**
 * 描述:RecyclerView 侧滑删除帮助类
 *
 * author zys
 * create by 2021/3/15
 */
internal class ItemSwipeHelper(maxSwipeWidth: Int) : ItemTouchHelper(SwipeCallback(maxSwipeWidth)) {

    private class SwipeCallback(private val maxSwipeWidth: Int) : ItemTouchHelper.Callback() {
        private var curPosition = -1
        private var currentScrollX = 0f
        private var flag = false

        private fun reset(recyclerView: RecyclerView, position: Int) {
            if (position != -1) {
                recyclerView.findViewHolderForAdapterPosition(position)?.itemView?.scrollX = 0
            }
        }

        override fun getMovementFlags(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder
        ): Int {
            if (!flag) {
                flag = true
                recyclerView.setOnItemClickListener { _, _ ->
                    reset(recyclerView, curPosition)
                }
            }
            if (viewHolder.adapterPosition != curPosition) {
                reset(recyclerView, curPosition)
                curPosition = viewHolder.adapterPosition
            }
            return makeMovementFlags(0, 12)
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
            if (actionState != ACTION_STATE_SWIPE) {
                return
            }
            if (viewHolder.layoutPosition != curPosition) {
                return
            }
            val view = viewHolder.itemView
            if (dX == 0f) {
                currentScrollX = view.scrollX.toFloat()
            }
            if (isCurrentlyActive) {//手指滑动
                if (dX < 0) {
                    view.scrollX = min((currentScrollX - dX).toInt(), maxSwipeWidth)
                } else if (dX > 0) {
                    view.scrollX = max((currentScrollX - dX).toInt(), 0)
                }
            } else {    //手指离开后
                if (dX < 0) {
                    view.scrollX = maxSwipeWidth
                } else if (dX > 0) {
                    view.scrollX = 0
                    curPosition = -1
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

    }
}
