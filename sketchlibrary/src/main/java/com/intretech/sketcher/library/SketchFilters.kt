package com.intretech.sketcher.library

import android.graphics.Point
import com.intretech.sketcher.library.filter.BrightnessFilter
import com.intretech.sketcher.library.filter.ContrastFilter
import com.intretech.sketcher.library.filter.CurveFilter
import com.intretech.sketcher.library.filter.Filter

class SketchFilters {

    fun lightFilter(): Filter {
        val rgbPoints: Array<Point> = emptyArray()
        rgbPoints[0] = Point(0, 0)
        rgbPoints[1] = Point(34, 6)
        rgbPoints[2] = Point(69, 23)
        rgbPoints[3] = Point(100, 58)
        rgbPoints[4] = Point(150, 154)
        rgbPoints[5] = Point(176, 196)
        rgbPoints[6] = Point(207, 233)
        rgbPoints[7] = Point(255, 255)
        val filter = Filter()
        filter.addFilter(CurveFilter(rgbPoints, null, null, null))
        return filter
    }

    fun blueFilter(): Filter {
        val redPoints: Array<Point> = emptyArray()
        redPoints[0] = Point(0, 0)
        redPoints[1] = Point(86, 34)
        redPoints[2] = Point(117, 41)
        redPoints[3] = Point(146, 80)
        redPoints[4] = Point(170, 151)
        redPoints[5] = Point(200, 214)
        redPoints[6] = Point(225, 242)
        redPoints[7] = Point(255, 255)
        val filter = Filter()
        filter.addFilter(CurveFilter(null, redPoints, null, null))
        filter.addFilter(BrightnessFilter(30))
        filter.addFilter(ContrastFilter(1f))
        return filter
    }

    fun viberFilter(): Filter {
        val rgbPoints: Array<Point> = emptyArray()
        val redPoints: Array<Point> = emptyArray()
        val greenPoints: Array<Point> = emptyArray()
        val bluePoints: Array<Point> = emptyArray()

        rgbPoints[0] = Point(0, 0)
        rgbPoints[1] = Point(80, 43)
        rgbPoints[2] = Point(149, 102)
        rgbPoints[3] = Point(201, 173)
        rgbPoints[4] = Point(255, 255)

        redPoints[0] = Point(0, 0)
        redPoints[1] = Point(125, 147)
        redPoints[2] = Point(177, 199)
        redPoints[3] = Point(213, 228)
        redPoints[4] = Point(255, 255)


        greenPoints[0] = Point(0, 0)
        greenPoints[1] = Point(57, 76)
        greenPoints[2] = Point(103, 130)
        greenPoints[3] = Point(167, 192)
        greenPoints[4] = Point(211, 229)
        greenPoints[5] = Point(255, 255)


        bluePoints[0] = Point(0, 0)
        bluePoints[1] = Point(38, 62)
        bluePoints[2] = Point(75, 112)
        bluePoints[3] = Point(116, 158)
        bluePoints[4] = Point(171, 204)
        bluePoints[5] = Point(212, 233)
        bluePoints[6] = Point(255, 255)

        val filter = Filter()
        filter.addFilter(CurveFilter(rgbPoints, redPoints, greenPoints, bluePoints))
        return filter
    }

    fun stutterFilter(): Filter {
        val bluePoints: Array<Point> = emptyArray()
        bluePoints[0] = Point(0, 0)
        bluePoints[1] = Point(165, 114)
        bluePoints[2] = Point(255, 255)
        val filter = Filter()
        filter.addFilter(CurveFilter(null, null, null, bluePoints))
        return filter
    }

    fun whisperFilter(): Filter {
        val rgbPoints: Array<Point> = emptyArray()
        val redPoints: Array<Point> = emptyArray()
        val greenPoints: Array<Point> = emptyArray()
        val bluePoints: Array<Point> = emptyArray()

        rgbPoints[0] = Point(0, 0)
        rgbPoints[1] = Point(174, 109)
        rgbPoints[2] = Point(255, 255)

        redPoints[0] = Point(0, 0)
        redPoints[1] = Point(70, 114)
        redPoints[2] = Point(157, 145)
        redPoints[3] = Point(255, 255)

        greenPoints[0] = Point(0, 0)
        greenPoints[1] = Point(109, 138)
        greenPoints[2] = Point(255, 255)

        bluePoints[0] = Point(0, 0)
        bluePoints[1] = Point(113, 152)
        bluePoints[2] = Point(255, 255)

        val filter = Filter()
        filter.addFilter(CurveFilter(rgbPoints, redPoints, greenPoints, bluePoints))
        return filter
    }
}