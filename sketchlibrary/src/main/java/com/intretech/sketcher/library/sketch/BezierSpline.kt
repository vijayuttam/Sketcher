package com.intretech.sketcher.library.sketch

import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Point

object BezierSpline {

    fun curveGenerator(points: Array<Point>?): IntArray {
        if (points == null) {
            throw NullPointerException("points cannot be null")
        }

        val n = points.size - 1
        if (n < 1) {
            throw IllegalArgumentException("At least two points are required")
        }

        return getDevicePoints(points)
    }

    private fun getDevicePoints(points: Array<Point>): IntArray {
        val controlPoints = calculateControlPoints(points)
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(points[0].x.toFloat(), points[0].y.toFloat())
        path.moveTo(points[0].x.toFloat(), points[0].y.toFloat())

        for (index in 1 until points.size) {
            path.quadTo(controlPoints[index - 1].x.toFloat(), controlPoints[index - 1].y.toFloat(),
                    points[index].x.toFloat(), points[index].y.toFloat())
            path.moveTo(points[index].x.toFloat(), points[index].y.toFloat())
        }

        path.lineTo(255f, 255f)
        path.moveTo(255f, 255f)

        val allPoints = FloatArray(256)

        val pm = PathMeasure(path, false)
        for (i in 0..255) {
            allPoints[i] = -1f
        }

        var x = 0
        val coordinates = floatArrayOf(0f, 0f)

        do {
            val pathLength = pm.length
            var i = 0f
            while (i <= pathLength) {
                pm.getPosTan(i, coordinates, null)
                if (coordinates[0].toInt() > x && x < 256) {
                    allPoints[x] = coordinates[1]
                    x++
                }
                if (x > 255) {
                    break
                }
                i += 0.08f
            }
        } while (pm.nextContour())


        allPoints[0] = points[0].y.toFloat()
        for (i in 0..255) {
            if (allPoints[i] == -1f) {
                allPoints[i] = allPoints[i - 1]
            }
        }
        allPoints[255] = points[points.size - 1].y.toFloat()
        return validateCurve(allPoints)
    }

    private fun validateCurve(allPoints: FloatArray): IntArray {
        val curvedPath = IntArray(256)
        for (x in 0..255) {
            if (allPoints[x] > 255.0f) {
                curvedPath[x] = 255
            } else if (allPoints[x] < 0.0f) {
                curvedPath[x] = 0
            } else {
                curvedPath[x] = Math.round(allPoints[x])
            }
        }
        return curvedPath
    }


    private fun calculateControlPoints(points: Array<Point>): Array<Point> {
        val n = points.size - 1
        val controlPoints = Array(n, {Point(0,0)})

        if (n == 1) {
            controlPoints[0] = Point((2 * points[0].x + points[1].x) / 3,
                    (2 * points[0].y + points[1].y) / 3)
        } else {
            val rhs = FloatArray(n)

            for (i in 1 until n - 1) {
                rhs[i] = (4 * points[i].x + 2 * points[i + 1].x).toFloat()
            }
            rhs[0] = (points[0].x + 2 * points[1].x).toFloat()
            rhs[n - 1] = (8 * points[n - 1].x + points[n].x) / 2.0f

            val x = getFirstControlPoints(rhs)

            for (i in 1 until n - 1) {
                rhs[i] = (4 * points[i].y + 2 * points[i + 1].y).toFloat()
            }
            rhs[0] = (points[0].y + 2 * points[1].y).toFloat()
            rhs[n - 1] = (8 * points[n - 1].y + points[n].y) / 2.0f

            val y = getFirstControlPoints(rhs)
            for (i in 0 until n) {
                controlPoints[i] = Point(x[i].toInt(), y[i].toInt())
            }
        }

        return controlPoints
    }

    private fun getFirstControlPoints(rhs: FloatArray): FloatArray {
        val n = rhs.size
        val x = FloatArray(n)
        val tmp = FloatArray(n)

        var b = 1.0f
        x[0] = rhs[0] / b
        for (i in 1 until n)

        {
            tmp[i] = 1 / b
            b = (if (i < n - 1) 4.0f else 3.5f) - tmp[i]
            x[i] = (rhs[i] - x[i - 1]) / b
        }
        for (i in 1 until n) {
            x[n - i - 1] -= tmp[n - i] * x[n - i]
        }
        return x
    }
}