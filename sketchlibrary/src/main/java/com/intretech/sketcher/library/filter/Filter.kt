package com.intretech.sketcher.library.filter

import android.graphics.Bitmap

class Filter() {

    var subFilters: MutableList<IFilter> = ArrayList()

    fun addFilter(subFilter: IFilter) {
        subFilters.add(subFilter)
    }

    fun clearFilters() {
        subFilters.clear()
    }

    fun removeFilterWithTag(tag: String) {
        val iterator = subFilters.iterator()
        while (iterator.hasNext()) {
            val subFilter = iterator.next()
            if (subFilter.getTag().equals(tag)) {
                iterator.remove()
            }
        }
    }

    fun getFilterByTag(tag: String): IFilter? {
        for (subFilter in subFilters) {
            if (subFilter.getTag().equals(tag)) {
                return subFilter
            }
        }
        return null
    }


    fun processFilter(inputImage: Bitmap): Bitmap? {
        var outputImage: Bitmap? = inputImage
        if (outputImage != null) {
            for (subFilter in subFilters) {
                try {
                    outputImage = subFilter.process(outputImage!!)
                } catch (oe: OutOfMemoryError) {
                    System.gc()
                    try {
                        outputImage = subFilter.process(outputImage!!)
                    } catch (ignored: OutOfMemoryError) {
                    }
                }

            }
        }
        return outputImage
    }
}