package com.group.dev.ui.decoration_sticky

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.group.common.ext.dp
import kotlin.math.abs

/**
 * 描述:
 *
 * author zys
 * create by 2021/3/12
 */
class ItemDeleteHelper : ItemTouchHelper(Callback1())


private class Callback1 : ItemTouchHelper.Callback() {
    private val TAG = "测试TAG"
    private val maxSwipeWidth = 80.dp
    private var curPosition = -1

    private var adapter: IViewHolderDelete? = null
    override fun getMovementFlags(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder
    ): Int {
        Log.i(TAG, "getMovementFlags: ${viewHolder.layoutPosition}")
        adapter = recyclerView.adapter as? IViewHolderDelete
        if (viewHolder.layoutPosition != curPosition) {
            viewHolder.itemView.scrollX = 0
        }
        curPosition = viewHolder.layoutPosition
        return makeMovementFlags(0, 60)
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
        if (abs(dX) < maxSwipeWidth) {
            viewHolder.itemView.scrollX = -dX.toInt()
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.scrollX = 0
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return .3f
    }

}