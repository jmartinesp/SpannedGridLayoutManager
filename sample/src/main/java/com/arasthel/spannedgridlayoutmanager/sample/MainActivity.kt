package com.arasthel.spannedgridlayoutmanager.sample

import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import java.util.*

/**
 * Created by Jorge Martín on 24/5/17.
 */
class MainActivity: android.support.v7.app.AppCompatActivity() {

    val recyclerview: RecyclerView by lazy { findViewById(R.id.recyclerView) as RecyclerView }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val staggeredGridLayoutManager = SpannedGridLayoutManager(orientation = SpannedGridLayoutManager.Orientation.VERTICAL, spans = 4)
        recyclerview.layoutManager = staggeredGridLayoutManager

        recyclerview.addItemDecoration(SpaceItemDecorator(left = 10, top = 10, right = 10, bottom = 10))

        val adapter = GridItemAdapter()
        recyclerview.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.scrollTo -> recyclerview.layoutManager.scrollToPosition(Random().nextInt(500))
        }

        return super.onOptionsItemSelected(item)
    }

}