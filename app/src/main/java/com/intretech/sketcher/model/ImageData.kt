package com.intretech.sketcher.model

import android.graphics.Bitmap
import com.intretech.sketcher.library.filter.Filter

data class ImageData (var bitmap: Bitmap, var filter: Filter)