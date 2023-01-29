package com.group.dev.ui.decoration_sticky2

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

class DateVH(private val dateView: TextView) : RecyclerView.ViewHolder(dateView) {

    init {
        with(dateView) {
            layoutParams = ViewGroup.LayoutParams(-1, 50.dp)
            textSize = 20f
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
        }
    }

    fun bind(itemCell: DateCell) {
        dateView.text = itemCell.text
    }
}