package com.group.dev.ui.decoration_sticky2.core

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView

/**
 * 描述:
 *
 * author ZhaoYingShu
 * create by 2022/8/24
 */
class StickyRecyclerHeadersTouchListener(
    val recyclerView: RecyclerView,
    val decoration2: StickyDecoration
) : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector =
        GestureDetector(recyclerView.context, HeaderGestureListener())

    private val viewConfig = ViewConfiguration.get(recyclerView.context)

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//        val onTouchEvent = gestureDetector.onTouchEvent(e)
//        if (onTouchEvent){
//            return true
//        }
        if (e.action == MotionEvent.ACTION_DOWN) {
            return decoration2.applyTouchEvent(e,false)
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        gestureDetector.onTouchEvent(e)
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    private inner class HeaderGestureListener : GestureDetector.SimpleOnGestureListener() {


        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            if (e != null && decoration2.applyTouchEvent(e,true)) {
                return true
            }
            return false
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return distanceY > viewConfig.scaledTouchSlop
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            return true
        }
    }
}