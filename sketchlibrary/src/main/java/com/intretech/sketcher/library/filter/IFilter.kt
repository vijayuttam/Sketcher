package com.intretech.sketcher.library.filter

import android.graphics.Bitmap

interface IFilter {

    fun process(bitmap: Bitmap) : Bitmap

    fun getTag() : Any

    fun setTag(tag: Any)


}