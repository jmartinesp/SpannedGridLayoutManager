package com.arasthel.spannedgridlayoutmanager.sample

import android.support.v7.widget.RecyclerView
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager

/**
 * Created by Jorge Martín on 24/5/17.
 */
class MainActivity: android.support.v7.app.AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val staggeredGridLayoutManager = SpannedGridLayoutManager(orientation = SpannedGridLayoutManager.Orientation.VERTICAL, spans = 4)
        recyclerView.layoutManager = staggeredGridLayoutManager

        recyclerView.addItemDecoration(SpaceItemDecorator(left = 10, top = 10, right = 10, bottom = 10))

        val adapter = GridItemAdapter()
        recyclerView.adapter = adapter
    }

}