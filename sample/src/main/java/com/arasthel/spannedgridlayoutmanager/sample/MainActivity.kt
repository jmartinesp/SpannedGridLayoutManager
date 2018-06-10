package com.arasthel.spannedgridlayoutmanager.sample

import android.support.v7.widget.RecyclerView
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager

/**
 * Created by Jorge Martín on 24/5/17.
 */
class MainActivity: android.support.v7.app.AppCompatActivity() {

    val recyclerview: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val spannedGridLayoutManager = SpannedGridLayoutManager(orientation = SpannedGridLayoutManager.Orientation.VERTICAL, spans = 2)
        spannedGridLayoutManager.itemOrderIsStable = true

        /**
         * if you want use a custom height for item
         * spannedGridLayoutManager.setCustomHeight(200)
         */

        recyclerview.layoutManager = spannedGridLayoutManager

        val adapter = GridItemAdapter()
        recyclerview.adapter = adapter
    }

}