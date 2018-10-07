package com.kedar.ace.ui

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kedar.ace.R
import com.kedar.ace.model.RowsEntity


class ItemListAdapter internal constructor(context: Context) : RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mItems: List<RowsEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = mInflater.inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if (mItems != null)
            mItems!!.size
        else 0
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (mItems != null) {
            val current: RowsEntity = mItems!!.get(position)
            holder.txtTitle.text = current.mTitle
            holder.txtDescription.text = current.mDescription

            Glide.with(holder.imgThumbnail).load(current.mImagehref).into(holder.imgThumbnail)
        }
    }

    /**
     * Set items to adapter
     */
    internal fun setItems(rowEntries: List<RowsEntity>?) {
        mItems = rowEntries
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: AppCompatTextView = itemView.findViewById(R.id.txt_title)
        val txtDescription: AppCompatTextView = itemView.findViewById(R.id.txt_description)
        val imgThumbnail: ImageView = itemView.findViewById(R.id.img_thumbnail)
    }
}