package com.group.dev.ui.fish

import android.graphics.PointF
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/25
 */
object MathUtil {


    /**
     * 根据起点 线长 和角度 计算 终点坐标
     * @param startPointF   起始点坐标
     * @param length        要求的点到起始点的直线距离 -- 线长
     * @param angle         偏移角度
     */
     fun calculatePoint(startPointF: PointF, length: Float, angle: Float): PointF {
        val endX = cos(Math.toRadians(angle * 1.0)) * length
        val endY = sin(Math.toRadians(angle * 1.0 - 180)) * length
        return PointF(startPointF.x + endX.toFloat(), startPointF.y + endY.toFloat())
    }

    /**
     * 计算∠AoB 的值
     */
     fun includeAngle(O: PointF, A: PointF, B: PointF): Float {
        val aob = (A.x - O.x) * (B.x - O.x) + (A.y - O.y) * (B.y - O.y)

        val oa = sqrt((A.x - O.x) * (A.x - O.x) + (A.y - O.y) * (A.y - O.y))
        val ob = sqrt((B.x - O.x) * (B.x - O.x) + (B.y - O.y) * (B.y - O.y))

        val cosAoB = aob / (oa * ob)
        val angleAob = Math.toDegrees(acos(cosAoB).toDouble()).toFloat()
        val direction = (A.y - B.y) / (A.x - B.x) - (O.y - B.y) / (O.x - B.x)
        return when {
            direction == 0f -> if (aob > 0) 0f else 180f
            direction > 0f -> -angleAob
            else -> angleAob
        }
    }
}