package com.group.dev.widget
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.group.dev.R
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.internal.InternalAbstract
import kotlinx.android.synthetic.main.lottie_footer.view.*

/**
 * com.centa.zhaoys.widget.LottieFooter
 */
class LottieFooter : InternalAbstract, RefreshFooter {

    init {
        View.inflate(context, R.layout.lottie_footer, this)
    }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context, attrs, defStyleAttr
    )

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        lottieView.playAnimation()
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        lottieView.cancelAnimation()
        return super.onFinish(refreshLayout, success)
    }

    override fun setNoMoreData(noMoreData: Boolean) = when (noMoreData) {
        true -> {
            lottieView.visibility = View.GONE
            footerTips.visibility = View.VISIBLE
            true
        }
        else -> {
            lottieView.visibility = View.VISIBLE
            footerTips.visibility = View.GONE
            true
        }
    }
}