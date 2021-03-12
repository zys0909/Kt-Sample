package com.group.dev.ui.decoration_sticky

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group.dev.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
class DateCell(val text: String) {
    val month: String = parse(text)

    private fun parse(text: String): String {
        val parse = SimpleDateFormat("yyyy 年 MM 月 dd 日", Locale.CHINA).parse(text)
        return SimpleDateFormat(" yyyy-MM", Locale.CHINA).format(parse)
    }

}

class DateVH(val itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val dateView = itemView.findViewById<TextView>(R.id.tv_date)

    fun bind(dateCell: DateCell) {
        dateView.text = dateCell.text
    }

}