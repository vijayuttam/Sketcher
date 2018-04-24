package com.intretech.sketcher

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path

object SketchUtil {

    fun generateCircularBitmap(bitmap: Bitmap) : Bitmap {
        val width: Int = bitmap.width;
        val height: Int = bitmap.height
        val path = Path()
        var newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        path.addCircle((width/2).toFloat(), (height/2).toFloat(),
                Math.min(width, height/2).toFloat(), Path.Direction.CCW)

        val canvas = Canvas(newBitmap)
        canvas.clipPath(path)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        return newBitmap
    }
}