package com.group.dev.ui.decoration_sticky

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group.common.ext.dp
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

class DateVH(private val textView: TextView) : RecyclerView.ViewHolder(textView) {

    init {
        textView.setBackgroundColor(0xFF999999.toInt())
        textView.textSize = 20f
        textView.setTextColor(Color.BLACK)
        textView.gravity = Gravity.CENTER
        val params = ViewGroup.LayoutParams(-1, 50.dp)
        textView.layoutParams = params
    }

    fun bind(dateCell: DateCell) {
        textView.text = dateCell.text
    }

}