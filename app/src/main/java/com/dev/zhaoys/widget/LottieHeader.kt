package com.dev.zhaoys.widget
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.dev.zhaoys.R
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.internal.InternalAbstract
import kotlinx.android.synthetic.main.lottie_header.view.*

/**
 * com.centa.zhaoys.widget.LottieHeader
 *
 * Created by shengl on 2018/12/21.
 */
class LottieHeader : InternalAbstract, RefreshHeader {

    init {
        View.inflate(context, R.layout.lottie_header, this)
    }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight)
        if (isDragging) {
            lottieView.progress = 1.0f * offset / maxDragHeight
        }
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        lottieView.playAnimation()
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        lottieView.cancelAnimation()
        return super.onFinish(refreshLayout, success)
    }
}