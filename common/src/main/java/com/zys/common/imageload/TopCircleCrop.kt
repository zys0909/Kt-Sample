package com.zys.common.imageload

import android.graphics.Bitmap
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest

/**
 * 描述:截取图片上半部分的圆形
 *
 * author zhaoys
 * create by 2019/9/3 0003
 */
class TopCircleCrop : BitmapTransformation() {
    companion object {
        private const val ID = "com.zys.common.imageload.TopCircleCrop"
        private val ID_BYTES = ID.toByteArray(Key.CHARSET)
    }

    // Bitmap doesn't implement equals, so == and .equals are equivalent here.
    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val createBitmap = Bitmap.createBitmap(toTransform, 0, 0, width, width)
        return TransformationUtils.circleCrop(pool, createBitmap, outWidth, outHeight)
    }

    override fun equals(other: Any?): Boolean {
        return other is TopCircleCrop
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }
}