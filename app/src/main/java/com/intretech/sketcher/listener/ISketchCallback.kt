package com.intretech.sketcher.listener

import com.intretech.sketcher.library.filter.Filter

interface ISketchCallback {

    fun onItemSelected(filter: Filter)
}