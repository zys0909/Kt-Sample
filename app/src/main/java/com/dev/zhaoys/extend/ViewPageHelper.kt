package com.dev.zhaoys.extend

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.dev.zhaoys.utils.SysUtil
import java.lang.ref.WeakReference

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/7/25 0025
 */
class ViewPageHelper(private val viewPager2: ViewPager2) {
    private var running = false
    private val liveData by lazy {
        MyLiveData()
    }

    companion object {
        private const val TIME_AUTO_POLL = 5000L
    }

    @SuppressLint("ClickableViewAccessibility")
    fun init() {
        val activity = viewPager2.context.getActivity()
        viewPager2.setOnTouchListener { _, event ->
            event?.let {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (running) stop()
                        viewPager2.performClick()
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                        start()
                    }
                    else -> {

                    }
                }
            }
            false
        }
        activity?.let {
            liveData.observe(it, Observer { b ->
                if (b) start() else stop()
            })
        }
    }

    private val task: AutoPollTask
        get() = AutoPollTask(viewPager2)

    private fun start() {
        if (running) stop()
        running = true
        viewPager2. postDelayed(task, TIME_AUTO_POLL)
    }

    private fun stop() {
        running = false
        viewPager2. removeCallbacks(task)
    }

    private inner class AutoPollTask(autoRecyclerView: ViewPager2) : Runnable {

        private val reference: WeakReference<ViewPager2> = WeakReference(autoRecyclerView)

        override fun run() {
            val viewPager2 = reference.get()
            if (viewPager2 != null && running) {
                val position = viewPager2.currentItem
                if (position + 1 < viewPager2.childCount) {
                    viewPager2.currentItem = position + 1
                    viewPager2.postDelayed(task, TIME_AUTO_POLL)
                }
            }
        }
    }

    private class MyLiveData : MutableLiveData<Boolean>() {
        override fun onActive() {
            value = true
        }

        override fun onInactive() {
            value = false
        }
    }
}