package com.intretech.sketcher.library.filter

import android.graphics.Bitmap
import android.graphics.Point
import com.intretech.sketcher.library.sketch.BezierSpline
import com.intretech.sketcher.library.sketch.ImageSketcher

class CurveFilter : IFilter {

    private var tag = ""
    private var rgbPoint: Array<Point>? = null
    private var green: Array<Point>? = null
    private var red: Array<Point>? = null
    private var blue: Array<Point>? = null
    private var rgb: IntArray? = null
    private var r: IntArray? = null
    private var g: IntArray? = null
    private var b: IntArray? = null

    constructor(rgbPoint: Array<Point>?, red: Array<Point>? , green: Array<Point>?, blue: Array<Point>?) {
        val straightPoint = emptyArray<Point>()
        straightPoint[0] = Point(0, 0)
        straightPoint[1] = Point(255, 255)
        if (this.rgbPoint == null) {
            this.rgbPoint = straightPoint
        } else {
            this.rgbPoint = rgbPoint
        }
        if (this.red == null) {
            this.red = straightPoint
        } else {
            this.red = red
        }
        if (this.green == null) {
            this.green = straightPoint
        } else {
            this.green = green
        }
        if (this.blue == null) {
            this.blue = straightPoint
        } else {
            this.blue = blue
        }
    }

    private fun sortPointsOnXAxis(points: Array<Point>?): Array<Point>? {
        if (points == null) {
            return null
        }
        for (s in 1 until points.size - 1) {
            for (k in 0..points.size - 2) {
                if (points[k].x > points[k + 1].x) {
                    var temp = 0f
                    temp = points[k].x.toFloat()
                    points[k].x = points[k + 1].x //swapping values
                    points[k + 1].x = temp.toInt()
                }
            }
        }
        return points
    }

    override fun process(bitmap: Bitmap): Bitmap {
        rgbPoint = sortPointsOnXAxis(rgbPoint)
        red = sortPointsOnXAxis(red)
        green = sortPointsOnXAxis(green)
        blue = sortPointsOnXAxis(blue)
        if (rgb == null) {
            rgb = BezierSpline.curveGenerator(
                    rgbPoint)
        }

        if (r == null) {
            r = BezierSpline.curveGenerator(red)
        }

        if (g == null) {
            g = BezierSpline.curveGenerator(green)
        }

        if (b == null) {
            b = BezierSpline.curveGenerator(blue)
        }

        return ImageSketcher.applyCurves(rgb, r, g,
                b, bitmap)
    }

    override fun getTag(): Any {
        return this.tag
    }

    override fun setTag(tag: Any) {
        this.tag = tag.toString()
    }
}