package com.example.movieapp.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ItemBinding
import com.example.movieapp.extensions.load
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieDetails
import com.example.movieapp.utils.getMoviesId
import com.example.movieapp.utils.openNextPage


class MoviesRecyclerAdapter : RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder>() {
    lateinit var loadPage: openNextPage
    private var data = mutableListOf<MovieDetails>()
    lateinit var getMoviesId : getMoviesId
    private lateinit var item: MovieDetails

    class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        item = data[position]
        with(holder.binding){
            mainImage.load(holder.binding.root.context, item.urlGenerator())
            titleTextView.text = item.title
            mainImage.setOnClickListener {
                getMoviesId.invoke(data[position].id)
            }
        }
    }

    override fun getItemCount() = data.size

    fun setData(data: Movie) {
        this.data.clear()
        loadPage.invoke(data.page!!)
        this.data = data.results!!.toMutableList()
        notifyDataSetChanged()
    }

    fun loadNextPage(data: Movie) {
        this.data.addAll(data.results!!)
        loadPage.invoke(data.page!!)
        notifyDataSetChanged()
    }

    fun setSavedData(data: MutableList<MovieDetails>) {
        this.data.clear()
        this.data = data
        notifyDataSetChanged()
    }
}