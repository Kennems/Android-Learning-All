package com.zhouguan.learnglide.transformation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class ColorFilterTransformation(private val color: Int) : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val newBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(newBitmap)
        val paint = Paint()
        paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        canvas.drawBitmap(newBitmap, 0f, 0f, paint)
        return newBitmap
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("ColorFilterTransformation".toByteArray())
        messageDigest.update(color.toString().toByteArray())
    }
}
