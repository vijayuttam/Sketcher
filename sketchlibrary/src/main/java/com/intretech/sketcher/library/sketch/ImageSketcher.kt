package com.intretech.sketcher.library.sketch

import android.graphics.Bitmap



object ImageSketcher {

    fun applyCurves(rgb: IntArray?, red: IntArray?, green: IntArray?, blue: IntArray?,
            inputImage: Bitmap): Bitmap {

        val width = inputImage.width
        val height = inputImage.height

        var pixels = IntArray(width * height)
        inputImage.getPixels(pixels, 0, width, 0, 0, width, height)

        if (rgb != null) {
            pixels = NativeSketcher.applyRGBCurve(pixels, rgb, width, height)
        }

        if (!(red == null && green == null && blue == null)) {
            pixels = NativeSketcher.applyChannelCurves(pixels, red!!, green!!, blue!!, width, height)
        }

        try {
            inputImage.setPixels(pixels, 0, width, 0, 0, width, height)
        } catch (ise: IllegalStateException) {
        }

        return inputImage
    }

    fun doBrightness(value: Int, inputImage: Bitmap): Bitmap {
        val width = inputImage.width
        val height = inputImage.height
        val pixels = IntArray(width * height)

        inputImage.getPixels(pixels, 0, width, 0, 0, width, height)
        NativeSketcher.doBrightness(pixels, value, width, height)
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height)

        return inputImage
    }

    fun doContrast(value: Float, inputImage: Bitmap): Bitmap {
        val width = inputImage.width
        val height = inputImage.height
        val pixels = IntArray(width * height)

        inputImage.getPixels(pixels, 0, width, 0, 0, width, height)
        NativeSketcher.doContrast(pixels, value, width, height)
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height)

        return inputImage
    }

    fun doColorOverlay(depth: Int, red: Float, green: Float, blue: Float,
            inputImage: Bitmap): Bitmap {
        val width = inputImage.width
        val height = inputImage.height
        val pixels = IntArray(width * height)

        inputImage.getPixels(pixels, 0, width, 0, 0, width, height)
        NativeSketcher.doColorOverlay(pixels, depth, red, green, blue, width, height)
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height)

        return inputImage
    }

    fun doSaturation(inputImage: Bitmap, level: Float): Bitmap {
        val width = inputImage.width
        val height = inputImage.height
        val pixels = IntArray(width * height)

        inputImage.getPixels(pixels, 0, width, 0, 0, width, height)
        NativeSketcher.doSaturation(pixels, level, width, height)
        inputImage.setPixels(pixels, 0, width, 0, 0, width, height)
        return inputImage
    }
}