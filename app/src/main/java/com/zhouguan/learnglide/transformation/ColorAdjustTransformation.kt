package com.zhouguan.learnglide.transformation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class ColorAdjustTransformation(
    private val brightness: Float,
    private val contrast: Float,
    private val saturation: Float
) : BitmapTransformation() {
    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        // 创建一个新的Bitmap，以便进行修改
        val newBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true)

        // 创建一个画布对象来操作图像
        val canvas = Canvas(newBitmap)

        // 创建Paint对象，用于处理亮度、对比度、饱和度
        val paint = Paint()

        // 创建一个色彩矩阵来调整图像
        val colorMatrix = ColorMatrix()

        // 设置亮度、对比度、饱和度
        colorMatrix.setSaturation(saturation) // 饱和度
        val contrastArray = floatArrayOf(
            contrast, 0f, 0f, 0f, 0f,
            0f, contrast, 0f, 0f, 0f,
            0f, 0f, contrast, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        )
        colorMatrix.postConcat(ColorMatrix(contrastArray))
        colorMatrix.postConcat(ColorMatrix().apply {
            setScale(
                brightness,
                brightness,
                brightness,
                1f
            )
        }) // 亮度

        // 将颜色矩阵应用到Paint
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)

        // 将Paint应用到Canvas
        canvas.drawBitmap(newBitmap, 0f, 0f, paint)

        return newBitmap
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("ColorAdjustTransformation".toByteArray())
        messageDigest.update(brightness.toString().toByteArray())
        messageDigest.update(contrast.toString().toByteArray())
        messageDigest.update(saturation.toString().toByteArray())
    }

}
