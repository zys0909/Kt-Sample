package com.group.dev.ui.other


import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group.common.base.RecyclerViewActivity
import com.group.common.core.ColorR
import com.group.common.ext.dp


/**
 * 描述:
 *
 * author zys
 * create by 2019-12-30
 */
class TestActivity : RecyclerViewActivity() {
    val chars = arrayOf(
        "🎵",
        "♪",
        "♩",
        "♫",
        "♬",
        "¶",
        "‖",
        "♭",
        "♯",
        "§",
        "∮",
        "※",
        "∴",
        "∵",
        "∽",
        "¥",
        "Ψ",
        "$"
    )


    override fun init(view: RecyclerView) {
        view.setPadding(20.dp, 80.dp, 20.dp, 0)
        view.setBackgroundColor(ColorR.C1C4CC)
        view.layoutManager = GridLayoutManager(this, 5)
    }
}

