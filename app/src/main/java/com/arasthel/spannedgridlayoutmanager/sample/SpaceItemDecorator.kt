package com.arasthel.spannedgridlayoutmanager.sample

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Jorge Martín on 2/6/17.
 */

class SpaceItemDecorator(val left: Int,
                         val top: Int,
                         val right: Int,
                         val bottom: Int): android.support.v7.widget.RecyclerView.ItemDecoration() {


    constructor(rect: android.graphics.Rect): this(rect.left, rect.top, rect.right, rect.bottom)

    override fun getItemOffsets(outRect: android.graphics.Rect?, view: android.view.View?, parent: android.support.v7.widget.RecyclerView?, state: android.support.v7.widget.RecyclerView.State?) {
        outRect?.left = this.left
        outRect?.top = this.top
        outRect?.right = this.right
        outRect?.bottom = this.bottom
    }

}