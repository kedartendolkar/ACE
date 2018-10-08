package com.kedar.ace.ui

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


class SpacesItemDecorator(private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect?.left = margin
        outRect?.right = margin
        outRect?.top = margin / 2 //Remove double margin
        outRect?.bottom = margin / 2 // Remove double margin

    }
}