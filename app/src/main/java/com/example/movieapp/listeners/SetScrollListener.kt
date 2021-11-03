package com.example.movieapp.listeners

import android.widget.AbsListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SetScrollListener(val call: () -> Unit) : RecyclerView.OnScrollListener() {

    var scrolling = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val recyclerLayoutManager = recyclerView.layoutManager as GridLayoutManager
        val lastItem = recyclerLayoutManager.findFirstVisibleItemPosition() + recyclerLayoutManager.childCount >= recyclerLayoutManager.itemCount
        val notAtBegin = recyclerLayoutManager.findFirstVisibleItemPosition() >= 1
        val moreThanShowedItems = recyclerLayoutManager.itemCount >= 20
        if (notAtBegin && scrolling && lastItem && notAtBegin && moreThanShowedItems ) {
            call()
            scrolling = false
        }

    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            scrolling = true
        }
    }
}