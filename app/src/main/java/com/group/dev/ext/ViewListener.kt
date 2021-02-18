package com.group.dev.ext

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator


inline fun View.debounceClick(crossinline click: (v: View) -> Unit = {}) {
    var latest = 0L
    setOnClickListener {
        val current = System.currentTimeMillis()
        if (current - latest > 500L) {
            latest = current
            click.invoke(it)
        }
    }
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