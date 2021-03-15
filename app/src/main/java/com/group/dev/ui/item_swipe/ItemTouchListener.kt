package com.group.dev.ui.item_swipe

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 描述: 处理 RecyclerView.OnItemTouchListener
 *
 * author zys
 * create by 2021/3/15
 */
internal class ItemTouchListener(
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

    private class CustomerItemOnGestureListener(
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
}