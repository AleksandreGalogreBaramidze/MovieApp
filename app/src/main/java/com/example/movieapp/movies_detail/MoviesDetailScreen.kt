
package com.example.movieapp.movies_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.base_fragment.BaseFragment
import com.example.movieapp.databinding.MoviesDetailScreenFragmentBinding
import kotlin.properties.Delegates

class MoviesDetailScreen : BaseFragment<MoviesDetailScreenFragmentBinding>(MoviesDetailScreenFragmentBinding::inflate) {
    private var isFavorite by Delegates.notNull<Boolean>()
    override fun init(layoutInflater: LayoutInflater, viewGroup: ViewGroup?) {
        init()
    }

    private fun init(){
        binding.backArrowIv.setOnClickListener {
            findNavController().navigateUp()
        }
        favoriteButtonLogic()
    }

    private fun favoriteButtonLogic(){
        isFavorite = false
        binding.favoriteStarImageView.setOnClickListener{
            isFavorite = if (isFavorite){
                binding.favoriteStarImageView.setImageResource(R.drawable.star)
                false
            }else{
                binding.favoriteStarImageView.setImageResource(android.R.drawable.star_off)
                true
            }
        }
    }
}