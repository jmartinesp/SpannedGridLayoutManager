/*
 * Copyright © 2017 Jorge Martín Espinosa
 */

package com.arasthel.spannedgridlayoutmanager

import android.graphics.Rect

/**
 * Created by Jorge Martín on 4/6/17.
 */


fun Rect.isAdjacentTo(rect: Rect): Boolean {
    return (this.right == rect.left
            || this.top == rect.bottom
            || this.left == rect.right
            || this.bottom == rect.top)
}

fun Rect.intersects(rect: Rect): Boolean {
    return this.intersects(rect.left, rect.top, rect.right, rect.bottom)
}