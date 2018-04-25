package com.intretech.sketcher

import android.content.Context
import android.graphics.Bitmap
import com.intretech.sketcher.model.ImageData

object SketchManager {

    private var filterImageData = ArrayList<ImageData>(10)
    private var processedImageDta = ArrayList<ImageData>(10)

    fun addData(imageData: ImageData) {
        filterImageData.add(imageData)
    }

    fun processData(context: Context): List<ImageData> {
        for (thumb in filterImageData) {
            val size = context.resources.getDimension(R.dimen.bitmapSize)
            thumb.bitmap = Bitmap.createScaledBitmap(thumb.bitmap, size.toInt(), size.toInt(), false)
            thumb.bitmap = thumb.filter.processFilter(thumb.bitmap)!!

            thumb.bitmap = SketchUtil.generateCircularBitmap(thumb.bitmap)
            processedImageDta.add(thumb)
        }
        return processedImageDta
    }

    fun clearData() {
        filterImageData = ArrayList()
        processedImageDta = ArrayList()
    }

}