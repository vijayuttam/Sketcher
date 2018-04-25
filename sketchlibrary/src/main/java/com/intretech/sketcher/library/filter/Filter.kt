package com.intretech.sketcher.library.filter

import android.graphics.Bitmap

class Filter() {

    var filters: MutableList<IFilter> = ArrayList()

    fun addFilter(subFilter: IFilter) {
        filters.add(subFilter)
    }

    fun clearFilters() {
        filters.clear()
    }

    fun removeFilterWithTag(tag: String) {
        val iterator = filters.iterator()
        while (iterator.hasNext()) {
            val subFilter = iterator.next()
            if (subFilter.getTag().equals(tag)) {
                iterator.remove()
            }
        }
    }

    fun getFilterByTag(tag: String): IFilter? {
        for (subFilter in filters) {
            if (subFilter.getTag().equals(tag)) {
                return subFilter
            }
        }
        return null
    }


    fun processFilter(inputBitmap: Bitmap): Bitmap? {
        var outputBitmap: Bitmap? = inputBitmap
        if (outputBitmap != null) {
            for (filter in filters) {
                try {
                    outputBitmap = filter.process(outputBitmap!!)
                } catch (oe: OutOfMemoryError) {
                    System.gc()
                    try {
                        outputBitmap = filter.process(outputBitmap!!)
                    } catch (ignored: OutOfMemoryError) {
                    }
                }
            }
        }
        return outputBitmap
    }
}