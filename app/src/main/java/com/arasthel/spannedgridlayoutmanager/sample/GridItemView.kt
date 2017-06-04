package com.arasthel.spannedgridlayoutmanager.sample

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView

/**
 * Created by Jorge Martín on 24/5/17.
 */
class GridItemView(context: Context?) : FrameLayout(context) {

    private var title: TextView

    init {
        title = TextView(context)
        title.gravity = Gravity.CENTER
        addView(title, 100, 100)
    }

    fun setTitle(text: String) {
        title.text = text

        requestLayout()
    }

}