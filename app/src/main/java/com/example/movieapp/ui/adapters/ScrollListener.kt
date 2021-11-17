package com.example.movieapp.ui.adapters

import android.widget.AbsListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.utils.Constants.ONE_PAGE

class ScrollListener(val call: () -> Unit,private val page: Int) : RecyclerView.OnScrollListener() {

    var scrolling = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val recyclerLayoutManager = recyclerView.layoutManager as GridLayoutManager
        val lastItem = recyclerLayoutManager.findFirstVisibleItemPosition() + recyclerLayoutManager.childCount >= recyclerLayoutManager.itemCount
        val notAtBegin = recyclerLayoutManager.findFirstVisibleItemPosition() >= ONE_PAGE
        val moreThanShowedItems = recyclerLayoutManager.itemCount >= page
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