package com.intretech.sketcher.library.filter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.intretech.sketcher.library.R

class VignetteFilter (val context: Context, private var alpha: Int) : IFilter {

    private var tag = ""

    override fun process(bitmap: Bitmap): Bitmap {
        var vignette = BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background)
        vignette = Bitmap.createScaledBitmap(bitmap, vignette.width, vignette.height, true)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.alpha = alpha

        var canvas = Canvas(bitmap)
        canvas.drawBitmap(vignette, 0f,0f, paint)

        return bitmap
    }

    override fun getTag(): Any {
        return this.tag
    }

    override fun setTag(tag: Any) {
        this.tag = tag.toString()
    }

    fun setAlpha(alpha: Int) {
        this.alpha = alpha
    }

    fun changeAlpha(value: Int) {
        this.alpha += value

        if (this.alpha > 255) {
            this.alpha = 255
        } else if (this.alpha < 0) {
            this.alpha = 0
        }
    }
}