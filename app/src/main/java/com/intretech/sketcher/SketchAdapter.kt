package com.intretech.sketcher

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.intretech.sketcher.listener.ISketchCallback
import com.intretech.sketcher.model.ImageData

class SketchAdapter (private val mutableList: MutableList<ImageData>,
                        private val listener: ISketchCallback) :
                        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var lastPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).
                inflate(R.layout.card_item, parent, false)

        return SketchViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if (mutableList.size == 0) 0
        else mutableList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mutableList[position]
        val viewHolder = SketchViewHolder(holder.itemView)
        viewHolder.imageView?.setImageBitmap(item.bitmap)
        viewHolder.imageView?.scaleType = ImageView.ScaleType.FIT_START

        viewHolder.imageView?.setOnClickListener {
            if (lastPosition != position) {
                listener.onItemSelected(item.filter)
            }
        }
    }


    inner class SketchViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView? = null
        init {
            imageView = itemView.findViewById(R.id.cardImageView)
        }
    }
}