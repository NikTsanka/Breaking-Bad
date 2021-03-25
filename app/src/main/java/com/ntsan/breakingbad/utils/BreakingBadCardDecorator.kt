package com.ntsan.breakingbad.utils

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView

class BreakingBadCardDecorator(
    @Dimension
    private val itemHorizontalSpacing: Int,
    @Dimension
    private val itemVerticalSpacing: Int,
    @Dimension
    private val itemHorizontalInsets: Int,
    @Dimension
    private val itemVerticalInsets: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0
        outRect.set(
            if (position % 2 == 0) itemHorizontalInsets else itemHorizontalSpacing / 2,
            if (position == 0 || position == 1) itemVerticalInsets else itemVerticalSpacing,
            if (position % 2 == 0) itemHorizontalSpacing / 2 else itemHorizontalInsets,
            if (position == itemCount - 1 || position == itemCount - 2) itemHorizontalInsets else 0
        )
    }
}