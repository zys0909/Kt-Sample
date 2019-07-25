package com.dev.zhaoys.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.dev.zhaoys.utils.SysUtil
import java.lang.ref.WeakReference

/**
 * AutoRecyclerView
 *
 * Created by shengl on 2019/1/30.
 */
class AutoRecyclerView(context: Context, attr: AttributeSet?, defStyleAttr: Int) :
    RecyclerView(context, attr, defStyleAttr) {
    companion object {
        const val TIME_AUTO_POLL = 5000L
    }

    private val liveData by lazy {
        MyLiveData()
    }
    private val task: AutoPollTask
    private var running = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    init {
        PagerSnapHelper().attachToRecyclerView(this)
        task = AutoPollTask(this)
        val activity = SysUtil.getActivity4Context(context)
        activity?.let {
            liveData.observe(it, Observer { b ->
                if (b) start() else stop()
            })
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        e?.let {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (running) stop()
                    performClick()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                    start()
                }
                else -> {

                }
            }
        }

        return super.onTouchEvent(e)
    }

    @RecyclerView.Orientation
    var orientation: Int
        set(value) {
            this.layoutManager = LinearLayoutManager(this.context, value, false)
        }
        get() = this.layoutManager.orientation

    /**
     * 布局管理器
     */
    private var layoutManager: LinearLayoutManager
        set(value) = this.setLayoutManager(value)
        get() = this.getLayoutManager() as LinearLayoutManager

    private fun start() {
        if (running) stop()
        running = true
        postDelayed(task, TIME_AUTO_POLL)
    }

    private fun stop() {
        running = false
        removeCallbacks(task)
    }

    private class AutoPollTask(autoRecyclerView: AutoRecyclerView) : Runnable {

        private val reference: WeakReference<AutoRecyclerView> = WeakReference(autoRecyclerView)

        override fun run() {
            val recyclerView = reference.get()
            if (recyclerView != null && recyclerView.running && recyclerView.layoutManager is LinearLayoutManager) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position + 1 < layoutManager.itemCount) {
                    recyclerView.smoothScrollToPosition(position + 1)
                    recyclerView.postDelayed(recyclerView.task, TIME_AUTO_POLL)
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