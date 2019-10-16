package com.dev.zhaoys.ui.sticky

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019-10-08
 */
interface ViewCallback {
    fun scrollToPosition(position: Int, isSectionHeader: Boolean, scrollToTop: Boolean)

    fun findViewHolderForAdapterPosition(position: Int): RecyclerView.ViewHolder?

    fun requestChildFocus(view: View)
}