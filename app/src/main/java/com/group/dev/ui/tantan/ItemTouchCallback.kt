package com.group.dev.ui.tantan

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.sqrt

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/21
 */
class ItemTouchCallback(private val prettyAdapter: PrettyAdapter) :
    ItemTouchHelper.SimpleCallback(0, 15) {
    override fun onMove(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val remove = prettyAdapter.dataList.removeAt(viewHolder.layoutPosition)
        prettyAdapter.dataList.add(0, remove)
        prettyAdapter.notifyDataSetChanged()
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val maxDistance = (recyclerView.width * 0.5f)
        val distance = sqrt((dX * dX + dY * dY))
        var fraction = distance / maxDistance

        if (fraction > 1) {
            fraction = 1f
        }

        // 显示的个数  4个
        val itemCount = recyclerView.childCount

        for (i in 0 until itemCount) {
            val view: View = recyclerView.getChildAt(i)
            val level = itemCount - i - 1
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.translationY =
                        CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP
                    view.scaleX =
                        1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP
                    view.scaleY =
                        1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP
                }
            }
        }
    }

    override fun getAnimationDuration(
        recyclerView: RecyclerView, animationType: Int, animateDx: Float, animateDy: Float
    ): Long {
        return 10
    }
}