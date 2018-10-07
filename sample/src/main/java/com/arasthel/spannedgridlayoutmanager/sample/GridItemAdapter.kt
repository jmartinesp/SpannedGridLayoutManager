package com.arasthel.spannedgridlayoutmanager.sample

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.arasthel.spannedgridlayoutmanager.SpanLayoutParams
import com.arasthel.spannedgridlayoutmanager.SpanSize
import java.util.*

/**
 * Created by Jorge Martín on 24/5/17.
 */
class GridItemAdapter: RecyclerView.Adapter<GridItemViewHolder>() {

    val clickedItems: MutableList<Boolean>

    init {
        clickedItems = MutableList(itemCount, { false })
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.MAGENTA, Color.YELLOW)

    override fun onViewAttachedToWindow(holder: GridItemViewHolder) {
        super.onViewAttachedToWindow(holder)

        val position = holder.adapterPosition

        (holder.itemView as? GridItemView)?.setTitle("$position")

        holder.itemView.setBackgroundColor(
                colors[position % colors.size]
        )

        holder.itemView.setOnClickListener {
            clickedItems[position] = !clickedItems[position]
            notifyItemChanged(position)
        }
    }

    override fun onBindViewHolder(holder: GridItemViewHolder, position: Int) {
        val width = if (clickedItems[position]) 2 else 1
        val height = if (clickedItems[position]) 2 else 1

        val spanSize = SpanSize(width, height)
        holder.itemView.layoutParams = SpanLayoutParams(spanSize)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return 500
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemViewHolder {
        val gridItemView = GridItemView(parent.context)

        return GridItemViewHolder(gridItemView)
    }
}

class GridItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)