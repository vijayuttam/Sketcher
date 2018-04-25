package com.intretech.sketcher

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.intretech.sketcher.library.DecodeUtil
import com.intretech.sketcher.library.SketchFilters
import com.intretech.sketcher.library.filter.Filter
import com.intretech.sketcher.listener.ISketchCallback
import com.intretech.sketcher.model.ImageData
import java.io.FileNotFoundException

class MainActivity : AppCompatActivity(), ISketchCallback {

    private var mRecyclerView: RecyclerView? = null
    private var activity: AppCompatActivity? = null
    private var mImageView: AppCompatImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        activity = this
        mRecyclerView = findViewById(R.id.mrecyclerView)
        mImageView = findViewById(R.id.imageView)
        mImageView?.setImageBitmap(DecodeUtil.decodeSampledBitmapFromResource(resources,
                R.drawable.windmillsnetherlands, 300, 300))

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayout.HORIZONTAL
        linearLayoutManager.scrollToPosition(0)
        mRecyclerView?.layoutManager = linearLayoutManager
        mRecyclerView?.setHasFixedSize(true)

        addDataToAdapter()
    }

    private fun addDataToAdapter() {
        val handler = Handler()
        val runnable = Runnable {
            val bitmap = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(resources, R.drawable.windmillsnetherlands),
                    300,300, false)
            val t1 = ImageData(bitmap, SketchFilters.lightFilter())
            val t2 = ImageData(bitmap, SketchFilters.lightFilter())
            val t3 = ImageData(bitmap, SketchFilters.blueFilter())
            val t4 = ImageData(bitmap, SketchFilters.viberFilter())
            val t5 = ImageData(bitmap, SketchFilters.stutterFilter())
            val t6 = ImageData(bitmap, SketchFilters.whisperFilter(this))

            SketchManager.clearData()
            SketchManager.addData(t1)
            SketchManager.addData(t2)
            SketchManager.addData(t3)
            SketchManager.addData(t4)
            SketchManager.addData(t5)
            SketchManager.addData(t6)

            val mutableData = SketchManager.processData(this)

            val adapter = SketchAdapter(mutableData as MutableList<ImageData>,
                    activity as ISketchCallback)
            mRecyclerView?.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        handler.post(runnable)
    }

    override fun onResume() {
        super.onResume()
        try{
            System.loadLibrary("native-lib")
        } catch (fileException: FileNotFoundException) {
            Log.e(javaClass.name, fileException.toString())
        }

    }


    override fun onItemSelected(filter: Filter) {
        mImageView?.setImageBitmap(filter.processFilter(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.windmillsnetherlands),
                300, 300, false)))
    }
}