package com.dev.zhaoys

import kotlin.math.log10

/**
 * 描述:
 *
 * author zys
 * create by 2019-12-17
 */
object Value {
    val imageUrl = arrayOf(
        "https://ws1.sinaimg.cn/large/0065oQSqgy1fxd7vcz86nj30qo0ybqc1.jpg"
        , "http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg"
        , "https://ws1.sinaimg.cn/large/0065oQSqly1fytdr77urlj30sg10najf.jpg"
        , "https://ws1.sinaimg.cn/large/0065oQSqgy1fze94uew3jj30qo10cdka.jpg"
        , "https://ws1.sinaimg.cn/large/0065oQSqgy1fy58bi1wlgj30sg10hguu.jpg"
        , "https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg"
        , "https://ws1.sinaimg.cn/large/0065oQSqly1fw8wzdua6rj30sg0yc7gp.jpg"
        , "https://ws1.sinaimg.cn/large/0065oQSqly1fw0vdlg6xcj30j60mzdk7.jpg"
    )

    var color = arrayOf(
        "#F44336", "#E91E63", "#9C27B0", "#673AB7",
        "#3F51B5", "#2196F3", "#00BCD4", "#009688",
        "#4CAF50", "#CDDC39", "#FF9800", "#FF5722"
    )
    fun getNumberDigits(number: Int): Int {
        return if (number <= 0) 0 else (log10(number.toDouble()) + 1).toInt()
    }

}