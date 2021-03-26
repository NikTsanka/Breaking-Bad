package com.ntsan.breakingbad.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoadMoreListener(val callback: () -> Unit) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val gridLayoutManager = (recyclerView.layoutManager as GridLayoutManager)
        if (recyclerView.adapter?.itemCount == gridLayoutManager.findLastVisibleItemPosition() + 1) {
            callback()
        }
    }
}

