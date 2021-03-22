package com.ntsan.breakingbad.ui.fragments.cardDetails

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoadMoreListener(private val callBack: () -> Unit) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val gridLayoutManager = (recyclerView.layoutManager as GridLayoutManager)
        if (recyclerView.adapter?.itemCount == gridLayoutManager.findLastVisibleItemPosition() + 1) {
            callBack
        }
    }
}