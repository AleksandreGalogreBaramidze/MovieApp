package com.example.movieapp.adapters


import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.database.Entity
import com.example.movieapp.databinding.ItemBinding
import com.example.movieapp.extensions.load
import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Constants.BASE_PICTURE_URL
import com.example.movieapp.utils.getId
import com.example.movieapp.utils.openNextPage


class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {
    lateinit var loadPage: openNextPage
    private var data = mutableListOf<Movie.Result>()
    lateinit var getId : getId
    private lateinit var item: Movie.Result
    private var page = 1

    class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        item = data[position]
        holder.binding.mainImage.load(holder.binding.root.context, BASE_PICTURE_URL + (item.backdrop_path))
        holder.binding.titleTextView.text = item.title
        holder.binding.mainImage.setOnClickListener {
            data[position].id?.let { it1 -> getId(it1) }
        }
        if (position == data.size - 1) {
            loadPage(page + 1)
        }
    }

    override fun getItemCount() = data.size

    fun getData(data: Movie) {
        this.data.clear()
        this.data = data.results!!.toMutableList()
        notifyDataSetChanged()
    }

    fun loadNextPage(data: Movie) {
        this.data.addAll(data.results!!)
        this.page = data.page!!
        notifyDataSetChanged()
    }

    fun getSavedData(data: List<Entity>) {
        this.data.clear()
        val favoriteMovies: MutableList<Movie.Result> = mutableListOf()
        data.forEach { favoriteMovies.add(Movie.Result(it.poster, it.id, "unknown", "unknown", 0.0, "unknown", "unknown","",false, null)) }
        this.data = favoriteMovies
        notifyDataSetChanged()
    }
}