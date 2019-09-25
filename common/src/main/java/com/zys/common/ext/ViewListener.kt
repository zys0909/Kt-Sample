package com.zys.common.ext

import android.view.View


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