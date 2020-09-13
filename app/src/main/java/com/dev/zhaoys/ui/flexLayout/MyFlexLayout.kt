package com.dev.zhaoys.ui.flexLayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.dev.zhaoys.extend.dp

/**
 * 描述:自定义流式布局
 *
 * author zys
 * create by 2020/09/12
 */
class MyFlexLayout @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttr, defStyleRes) {

    private val horizontalSpacing = 4.dp
    private val verticalSpacing = 4.dp
    private val allLineViews = mutableListOf<List<View>>()
    private val allLineHeights = mutableListOf<Int>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        allLineViews.clear()
        allLineHeights.clear()
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)
        val selfHeight = MeasureSpec.getSize(heightMeasureSpec)
        var needWidth = 0    //子控件需要的宽
        var needHeight = 0   //子控件需要的高

        val lineViews = mutableListOf<View>()

        var curLineWidthUsed = 0
        var curLineHeight = 0

        for (i in 0..childCount) {
            val childView = getChildAt(i)
            if (childView.visibility != View.GONE) {
                val childLp = childView.layoutParams
                //将Child的LayoutParams转变为MeasureSpec
                val childWidthSpec =
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childLp.width)
                val childHeightSpec =
                    getChildMeasureSpec(
                        heightMeasureSpec,
                        paddingTop + paddingBottom,
                        heightMeasureSpec
                    )
                //子View的测量
                childView.measure(childWidthSpec, childHeightSpec)
                val measuredWidth = childView.measuredWidth
                val measuredHeight = childView.measuredHeight
                //换行
                if (measuredWidth + curLineWidthUsed + horizontalSpacing > selfWidth) {
                    allLineViews.add(lineViews)
                    allLineHeights.add(curLineHeight)
                    needWidth = needWidth.coerceAtLeast(curLineWidthUsed + horizontalSpacing)
                    needHeight += curLineHeight + verticalSpacing

                    lineViews.clear()
                    curLineWidthUsed = 0
                    curLineHeight = 0
                }
                lineViews.add(childView)
                //记录当前行 已使用的width
                curLineWidthUsed += measuredHeight + horizontalSpacing
                //记录当前行
                curLineHeight = curLineHeight.coerceAtLeast(measuredHeight)
                if (i == childCount - 1) {
                    allLineViews.add(lineViews)
                    allLineHeights.add(curLineHeight)
                    needWidth = Math.max(needWidth, curLineWidthUsed + horizontalSpacing)
                    needHeight += curLineHeight + verticalSpacing
                }
            }
        }

        //测量自身
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val realWidth = if (widthMode == MeasureSpec.EXACTLY) selfWidth else needWidth
        val realHeight = if (heightMode == MeasureSpec.EXACTLY) selfHeight else needHeight
        setMeasuredDimension(realWidth, realHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var curL = paddingLeft
        var curT = paddingTop
        for (i in 0..allLineViews.size) {
            val lineViews = allLineViews[i]
            val lineHeight = allLineHeights[i]
            for (j in 0..lineViews.size) {
                val view = lineViews[j]
                val curR = curL + view.measuredWidth
                val curB = curT + view.measuredHeight
                view.layout(curL, curT, curR, curB)
                curL = curR + horizontalSpacing
            }
            curT += lineHeight + verticalSpacing
            curL = paddingLeft
        }
    }
}