package com.group.common.ext

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.*

/**
 * 描述:  RecyclerView 扩展
 *
 * author zys
 * create by 2021/3/5
 */
var RecyclerView.orientation: Int
    set(value) {
        this.layoutManager = LinearLayoutManager(this.context, value, false)
    }
    get() = (this.layoutManager as LinearLayoutManager).orientation

val RecyclerView.LayoutManager.spanCount: Int
    get() = when (this) {
        is GridLayoutManager -> this.spanCount
        is StaggeredGridLayoutManager -> this.spanCount
        else -> throw IllegalArgumentException("layoutManager must be GridLayoutManager or StaggeredGridLayoutManager")
    }

fun RecyclerView.closeAnim() {
    this.itemAnimator?.apply {
        changeDuration = 0
        addDuration = 0
        removeDuration = 0
        moveDuration = 0
        (this as? SimpleItemAnimator)?.supportsChangeAnimations = false
    }
}

/**
 * 为RecyclerView增加Item点击回调
 */
fun RecyclerView.setOnItemClickListener(listener: (View, Int) -> Unit) {
    this.addOnItemTouchListener(CustomerItemTouchListener(this, listener))
}

class CustomerItemTouchListener(
    val recyclerView: RecyclerView,
    val listener: (View, Int) -> Unit
) : RecyclerView.OnItemTouchListener {

    private val gestureListener = CustomerItemOnGestureListener(recyclerView, listener)
    private val gestureDetector = GestureDetector(recyclerView.context, gestureListener)

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(e)
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}

class CustomerItemOnGestureListener(
    val recyclerView: RecyclerView,
    val listener: (View, Int) -> Unit
) : GestureDetector.OnGestureListener {
    override fun onDown(e: MotionEvent?): Boolean = false

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        e?.let {
            recyclerView.findChildViewUnder(it.x, it.y)
        }?.also {
            listener.invoke(it, recyclerView.getChildAdapterPosition(it))
        }
        return false
    }

    override fun onScroll(
        e1: MotionEvent?, e2: MotionEvent?,
        distanceX: Float, distanceY: Float
    ): Boolean = false

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onFling(
        e1: MotionEvent?, e2: MotionEvent?,
        velocityX: Float, velocityY: Float
    ): Boolean = false
}