package com.group.dev.ui.flextag

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.group.common.R
import com.group.common.ext.dp
import com.group.common.ext.dpf
import kotlin.math.max

/**
 *  描述: 自适应标签
 *
 *  参数介绍
 *
 *      最大行数 默认为1，单行
 *      app:flexTag_max_lines="1"
 *
 *      最大子View数量，默认为-1，即不限制
 *      app:flexTag_max_count="3"
 *
 *      标签之间的间隔距离 (水平方向)
 *      app:flexTag_horizontal_space="4dp"
 *
 *      标签之间的间隔距离 (竖直方向)
 *      app:flexTag_vertical_space="1dp"
 *
 *      标签字体颜色
 *      app:flexTag_textColor="#000000"
 *
 *      标签字体大小，默认10dp
 *      app:flexTag_textSize="10dp"
 *
 *      标签左右padding ，默认 3dp
 *      app:flexTag_paddingStartEnd="2dp"
 *
 *      标签上下padding ，默认0dp
 *      app:flexTag_paddingTopBottom="0dp"
 *
 * 以下四项属性 需配合 FlexTextItem 使用
 * @see FlexTextItem
 * @sample addTags
 *
 *      背景填充色，默认透明
 *      app:flexTag_fill_color="#FFFFFF"
 *
 *      背景边框圆角半径，为0 ，表示没有圆角，默认 1dp
 *      app:flexTag_radius="1dp"
 *
 *      背景边框颜色 ，默认透明
 *      app:flexTag_stroke_color="#2F2A3A"
 *
 *      背景边框宽度，默认0.5dp
 *      app:flexTag_stroke_width="0.5dp"
 *
 */
class FlexTagLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {
    /**
     * 标签之间的间隔距离 (水平方向)
     */
    var horizontalSpacing = 1.5.dp

    /**
     * 标签之间的间隔距离 (竖直方向)
     */
    var verticalSpacing = 0

    /**
     * 最大行数，
     */
    var maxLines = 1
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * 最大count， <0  即 不限制
     * 对于Visible  Gone ，InVisible ,不区分
     */
    var maxCount = -1
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * 标签字体颜色
     */
    @ColorInt
    var tagTextColor = Color.BLACK

    /**
     * 标签字号
     */
    var tagTextSize = 10.dpf

    /**
     * 背景填充色
     */
    var tagFillColor = Color.TRANSPARENT

    /**
     * 背景边框圆角半径，为0 ，表示没有圆角
     */
    var tagRadius = 1.dpf

    /**
     * 背景边框颜色
     */
    var tagStrokeColor = Color.TRANSPARENT

    /**
     * 背景边框宽度
     */
    var tagStrokeWidth = 0.5.dp

    var paddingStartEnd = 3.dp

    var paddingTopBottom = 0

    private val bgDrawable: Drawable
        get() = ViewUtil.createDrawable(tagFillColor, tagRadius, tagStrokeColor, tagStrokeWidth)


    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.FlexTagLayout, defStyleAttr, 0)
        val horizontalSpacing =
            a.getDimension(R.styleable.FlexTagLayout_flexTag_horizontal_space, 0f).toInt()
        if (horizontalSpacing > 0) {
            this.horizontalSpacing = horizontalSpacing
        }
        val verticalSpacing =
            a.getDimension(R.styleable.FlexTagLayout_flexTag_vertical_space, 0f).toInt()
        if (verticalSpacing > 0) {
            this.verticalSpacing = verticalSpacing
        }
        val maxLines = a.getInt(R.styleable.FlexTagLayout_flexTag_max_lines, 1)
        this.maxLines = maxLines

        val maxCount = a.getInt(R.styleable.FlexTagLayout_flexTag_max_count, -1)
        this.maxCount = maxCount

        val tagTextColor = a.getColor(R.styleable.FlexTagLayout_flexTag_textColor, 0)
        if (tagTextColor != 0) {
            this.tagTextColor = tagTextColor
        }
        val tagTextSize = a.getDimension(R.styleable.FlexTagLayout_flexTag_textSize, 0f)
        if (tagTextSize > 0) {
            this.tagTextSize = tagTextSize
        }
        val tagFillColor = a.getColor(R.styleable.FlexTagLayout_flexTag_fill_color, 0)
        if (tagFillColor != 0) {
            this.tagFillColor = tagFillColor
        }
        val tagRadius = a.getDimension(R.styleable.FlexTagLayout_flexTag_radius, 0f)
        if (tagRadius > 0) {
            this.tagRadius = tagRadius
        }
        val tagStrokeColor = a.getColor(R.styleable.FlexTagLayout_flexTag_stroke_color, 0)
        if (tagStrokeColor != 0) {
            this.tagStrokeColor = tagStrokeColor
        }
        val strokeWidth = a.getDimension(R.styleable.FlexTagLayout_flexTag_stroke_width, 0f)
        if (strokeWidth > 0) {
            this.tagStrokeWidth = strokeWidth.toInt()
        }
        val startEnd = a.getDimension(R.styleable.FlexTagLayout_flexTag_paddingStartEnd, 0f)
        if (startEnd > 0) {
            this.paddingStartEnd = startEnd.toInt()
        }
        val topBottom = a.getDimension(R.styleable.FlexTagLayout_flexTag_paddingTopBottom, 0f)
        if (topBottom > 0) {
            this.paddingTopBottom = topBottom.toInt()
        }
        a.recycle()
        setBackgroundColor(0)
    }

    /**
     * 清除所有标签
     */
    fun clear() {
        removeAllViews()
    }

    /**
     * 添加普通标签
     * 单一样式标签，
     */
    fun addTags(list: List<String>) {
        addTagView(list.map { FlexTextItem(it) })
    }

    /**
     * 设置标签
     */
    fun addTagsWithItem(list: List<FlexItem>) {
        if (list.isEmpty()) {
            return
        }
        addTagView(list)
    }


    private fun addTagView(list: List<FlexItem>) {
        removeAllViews()
        list.forEach {
            addView(createTagView(it))
        }
    }

    private fun createTagView(item: FlexItem): View = when (item) {
        is FlexTextItem -> AppCompatTextView(context).apply {
            // 标签
            text = item.text
            //设置字体颜色
            setTextColor(tagTextColor)
            //设置字号
            textSize = tagTextSize
            setTextSize(TypedValue.COMPLEX_UNIT_PX, tagTextSize)
            //设置背景
            background = bgDrawable
            //设置padding
            setPadding(paddingStartEnd, paddingTopBottom, paddingStartEnd, paddingTopBottom)
        }
        is FlexIconItem -> AppCompatImageView(context).apply {
            setImageResource(item.iconRes)
        }
        is FlexSpecialItem -> AppCompatTextView(context).apply {
            // 标签
            text = item.text
            //设置字体颜色
            setTextColor(item.textColor)
            //设置字号
            setTextSize(TypedValue.COMPLEX_UNIT_PX, item.textSize)
            //设置背景
            background = item.drawable
            //设置padding
            item.padding.run {
                setPadding(left, top, right, bottom)
            }
        }
    }


    private val allLineViews = mutableListOf<MutableList<View>>()
    private val lineHeights = mutableListOf<Int>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        allLineViews.clear()
        lineHeights.clear()
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)
        val selfHeight = MeasureSpec.getSize(heightMeasureSpec)

        var selfNeedWidth = 0
        var selfNeedHeight = 0
        var curLineViews = mutableListOf<View>()
        var curLineWithUsed = 0
        var curLineHeight = 0
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {

                measureChild(child, widthMeasureSpec, heightMeasureSpec)

                val childMeasuredWidth = child.measuredWidth
                val childMeasuredHeight = child.measuredHeight
                if (curLineWithUsed + childMeasuredWidth + horizontalSpacing + paddingLeft + paddingRight > selfWidth) {
                    allLineViews.add(curLineViews)
                    lineHeights.add(curLineHeight)
                    selfNeedWidth = max(selfNeedWidth, curLineWithUsed + horizontalSpacing)
                    selfNeedHeight += curLineHeight
                    curLineWithUsed = 0
                    curLineHeight = 0
                    curLineViews = mutableListOf()
                }

                curLineViews.add(child)
                curLineWithUsed += childMeasuredWidth + horizontalSpacing
                curLineHeight = max(curLineHeight, childMeasuredHeight + verticalSpacing)
                if (i == count - 1) {
                    allLineViews.add(curLineViews)
                    lineHeights.add(curLineHeight)
                    selfNeedWidth = max(selfNeedWidth, curLineWithUsed + horizontalSpacing)
                    selfNeedHeight += curLineHeight
                }
            }
        }
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val realWidth = if (widthMode == MeasureSpec.EXACTLY) selfWidth else selfNeedWidth
        val realHeight = if (heightMode == MeasureSpec.EXACTLY) selfHeight else selfNeedHeight
        setMeasuredDimension(realWidth, realHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = paddingLeft
        var right: Int
        var top = paddingTop
        var bottom: Int
        var layoutCount = 0
        for (i in 0 until allLineViews.size) {
            //判断是否超出最大行
            if (maxLines in 1..i) {
                break
            }
            val curLineViews = allLineViews[i]
            val curHeight = lineHeights[i]
            for (childView in curLineViews) {
                //判断是否超出最大数量
                if (maxCount in 0..layoutCount) {
                    break
                }
                right = left + childView.measuredWidth
                bottom = top + curHeight - verticalSpacing
                childView.layout(left, top, right, bottom)
                layoutCount++
                left += childView.measuredWidth + horizontalSpacing
            }
            top += curHeight + verticalSpacing
            left = paddingLeft
        }

    }

}

