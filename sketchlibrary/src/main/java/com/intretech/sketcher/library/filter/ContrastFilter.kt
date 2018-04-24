package com.intretech.sketcher.library.filter

import android.graphics.Bitmap
import com.intretech.sketcher.library.sketch.ImageSketcher

class ContrastFilter (val contrast: Float) : IFilter {

    private var tag = ""

    override fun process(bitmap: Bitmap): Bitmap {
        return ImageSketcher.doContrast(contrast, bitmap)
    }

    override fun getTag(): Any {
       return this.tag
    }

    override fun setTag(tag: Any) {
        this.tag = tag.toString()
    }
}