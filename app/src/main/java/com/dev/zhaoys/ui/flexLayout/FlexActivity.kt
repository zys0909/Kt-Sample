package com.dev.zhaoys.ui.flexLayout

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import com.dev.zhaoys.R
import com.dev.zhaoys.app.BaseActivity
import kotlinx.android.synthetic.main.activity_flex_layout.*

/**
 * 描述:
 *
 * author zys
 * create by 2020/09/12
 */
class FlexActivity : BaseActivity() {
    private val arr = arrayOf(
        "断魂涯", "顾名思义", "纵身一跳，身死魂断", "此时的魂断崖", "被黎明前的黑暗笼罩着", "显得特别的空灵",
        "悬涯下", "黑水河跳崖的咆哮声如雷声滚入耳中", "顺着每一根神经流淌", "游向神经末梢",
        "也许", "我们都需要", "这么一声呐喊或咆哮", "来驱除内心的怯懦与不甘", "为自己悲壮的人生送行",
        "悬崖顶上伫立着一块巨石", "它以这样的姿势", "静看世间冷暖已经多少年了？", "数十万年", "还是数千万年？", "谁也不知道",
        "氤氲于四周那种诡异的潮湿", "散发出一种", "深入骨髓的", "寒意", "让人没来由地感到", "一种痛彻心扉的冷", "不自禁地接连打着冷颤"
    )

    override fun layoutId() = R.layout.activity_flex_layout

    override fun init(savedInstanceState: Bundle?) {

        for (i in 0..30) {
            myFlexLayout.addView(AppCompatTextView(this).apply {
                text = arr[i % arr.size]
                setTextColor(Color.YELLOW)
            })
        }
    }
}