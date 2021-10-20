package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ItemBinding
import com.example.movieapp.extensions.load
import com.example.movieapp.models.Movie
import com.example.movieapp.utils.Constants.BASE_PICTURE_URL

class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {

    private var data = mutableListOf<Movie.Result>()

    inner class ViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: Movie.Result

        fun bind() {
            item = data[adapterPosition]
            binding.titleTextView.text = item.title
            binding.mainImage.load(binding.root.context, BASE_PICTURE_URL + (item.backdrop_path))

            itemView.setOnClickListener {
                //
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = data.size

    fun getData(data: Movie) {
        this.data.clear()
        this.data = data.results!!.toMutableList()
        notifyDataSetChanged()
    }


}