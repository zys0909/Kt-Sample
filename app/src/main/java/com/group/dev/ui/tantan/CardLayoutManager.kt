package com.group.dev.ui.tantan

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zys.ext.dpf

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/21
 */
class CardLayoutManager : RecyclerView.LayoutManager() {
    //屏幕最多显示的卡片数量

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (recycler == null) {
            return
        }
        // ViewHolder回收复用
        detachAndScrapAttachedViews(recycler)
        val itemCount = itemCount
        val lowestLayerPosition = if (itemCount < CardConfig.MAX_SHOW_COUNT) {
            0
        } else {
            itemCount - CardConfig.MAX_SHOW_COUNT
        }

        for (i in lowestLayerPosition until itemCount) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            //测量
            measureChildWithMargins(view, 0, 0)
            //布局
            val widthSpace = width - getDecoratedMeasuredWidth(view)
            val heightSpace = height - getDecoratedMeasuredHeight(view)
            val left = widthSpace / 2
            val top = heightSpace / 2
            val right = left + getDecoratedMeasuredWidth(view)
            val bottom = top + getDecoratedMeasuredHeight(view)
            layoutDecoratedWithMargins(view, left, top, right, bottom)

            val level = itemCount - i - 1
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.translationY = CardConfig.TRANS_Y_GAP * level
                    view.scaleX = 1 - CardConfig.SCALE_GAP * level
                    view.scaleY = 1 - CardConfig.SCALE_GAP * level
                } else {
                    // 最下面的那个view 与前一个view 布局一样
                    view.translationY = CardConfig.TRANS_Y_GAP * (level - 1)
                    view.scaleX = 1 - CardConfig.SCALE_GAP * (level - 1)
                    view.scaleY = 1 - CardConfig.SCALE_GAP * (level - 1)
                }
            }
        }
    }
}
