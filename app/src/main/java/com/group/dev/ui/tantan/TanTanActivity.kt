package com.group.dev.ui.tantan

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dev.zhaoys.app.RecyclerViewActivity
import com.zys.common.imageload.ImageLoader

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/21
 */
class TanTanActivity : RecyclerViewActivity() {

    private val array = listOf(
        Pic.M1,
        Pic.M2,
        Pic.M3,
        Pic.M4,
        Pic.M5,
        Pic.M6,
        Pic.M7,
        Pic.M8,
        Pic.M9,
        Pic.M10,
        Pic.M11,
        Pic.M12,
        Pic.M13,
        Pic.M14,
        Pic.M15,
        Pic.M16
    )

    private val cardData: List<PrettyCardCell>
        get() = array.mapIndexed { index, s -> PrettyCardCell("美女${index + 1}", s) }

    override fun init(view: RecyclerView) {
        val prettyAdapter = PrettyAdapter(ImageLoader(this@TanTanActivity))
        view.layoutManager = CardLayoutManager()
        view.adapter = prettyAdapter
        prettyAdapter.submit(cardData)
        val itemTouchCallback = ItemTouchCallback(prettyAdapter)
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(view)
    }
}