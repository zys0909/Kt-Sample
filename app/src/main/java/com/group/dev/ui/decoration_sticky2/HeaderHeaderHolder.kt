package com.group.dev.ui.decoration_sticky2

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.group.common.ext.toast
import com.group.dev.R
import com.group.dev.ui.decoration_sticky2.core.AbstractHeaderHolder

/**
 * 描述:
 *
 *
 * author ZhaoYingShu
 * create by 2022/8/23
 */
class HeaderHeaderHolder(itemView: View) : AbstractHeaderHolder(itemView) {
    private val mTvDate: AppCompatTextView
    private val mTvDelete: AppCompatTextView
    private var month: String? = null

    override fun bind(itemCell: DateCell, isFirst: Boolean) {
        month = itemCell.month
        mTvDate.text = month
        mTvDelete.visibility = if (isFirst) View.VISIBLE else View.GONE
    }

    init {
        mTvDate = itemView.findViewById(R.id.tv_date)
        mTvDelete = itemView.findViewById(R.id.tv_delete)
        itemView.setOnClickListener {
            it.context.toast(month)
        }
        mTvDelete.setOnClickListener {
            it.context.toast("点击 -- $month")
        }
    }
}