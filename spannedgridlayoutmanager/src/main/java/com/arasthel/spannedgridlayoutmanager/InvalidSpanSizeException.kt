package com.arasthel.spannedgridlayoutmanager

/**
 * Exception thrown when the SpanSize of the item is smaller than 1 or bigger than the layout
 * manager's max span size.
 */
class InvalidSpanSizeException(errorSize: Int, maxSpanSize: Int) :
        RuntimeException("Invalid item span size: $errorSize. Span size must be in the range: (1...$maxSpanSize)")