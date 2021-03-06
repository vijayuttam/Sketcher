package com.intretech.sketcher.library.filter

import android.graphics.Bitmap
import com.intretech.sketcher.library.sketch.ImageSketcher

class SaturationFilter(private var level: Float) : IFilter {

    private var tag = ""

    override fun process(bitmap: Bitmap): Bitmap {
        return ImageSketcher.doSaturation(bitmap, level)
    }

    override fun getTag(): Any {
        return this.tag
    }

    override fun setTag(tag: Any) {
        this.tag = tag.toString()
    }

    fun setLevel(level: Float) {
        this.level = level
    }
}