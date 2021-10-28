
package com.example.movieapp.movies_detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.api.ApiErrorHandling
import com.example.movieapp.base_fragment.BaseFragment
import com.example.movieapp.database.Entity
import com.example.movieapp.databinding.MoviesDetailScreenFragmentBinding
import com.example.movieapp.extensions.load
import com.example.movieapp.extensions.toast
import com.example.movieapp.utils.Constants.BASE_PICTURE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesDetailScreen : BaseFragment<MoviesDetailScreenFragmentBinding>(MoviesDetailScreenFragmentBinding::inflate) {
    private val viewModel: MoviesDetailScreenViewModel by viewModels()
    private val safeArgs: MoviesDetailScreenArgs by navArgs()
    private lateinit var pictureUrl :String
    override fun init(layoutInflater: LayoutInflater, viewGroup: ViewGroup?) {
        viewModel.movieDetails(safeArgs.id)
        viewModel.alreadyFav(safeArgs.id)
        binding.backArrowIv.setOnClickListener {
            findNavController().navigateUp()
        }
//        favoriteButtonLogic()
        observes()
    }


//    private fun favoriteButtonLogic(){
//        isFavorite = false
//        binding.favoriteStarImageView.setOnClickListener{
//            if (isFavorite){
//                binding.favoriteStarImageView.setImageResource(R.drawable.star)
//                true
//            }else{
//                binding.favoriteStarImageView.setImageResource(android.R.drawable.star_off)
//                false
//            }
//        }
//    }

    @SuppressLint("SetTextI18n")
    private fun observes() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            when (it.status) {
                ApiErrorHandling.Status.Success -> {
                    pictureUrl = it.data!!.posterPath
                    with(binding) {
                        mainImage.load(requireContext(), BASE_PICTURE_URL + it.data.posterPath)
                        detailMovieTitleTxt.text = it.data.originalTitle
                        detailMovieRatingTxt.text = "Rating: " + it.data.voteAverage.toString()
                        detailMovieOverViewTxt.text = it.data.overview
                    }
                }
                ApiErrorHandling.Status.Error -> {
                    it.message?.toast(requireContext())
                }
            }
        })

        viewModel.checkerLiveData.observe(viewLifecycleOwner, {
            if(it == true){
                with(binding){
                    favoriteStarImageView.setImageResource(R.drawable.star)
                    favoriteStarImageView.setOnClickListener {
                        viewModel.deleteFromFav(safeArgs.id)
                        viewModel.alreadyFav(safeArgs.id)
                    }
                }
            }else{
                with(binding){
                    favoriteStarImageView.setImageResource(android.R.drawable.star_off)
                    favoriteStarImageView.setOnClickListener {
                        viewModel.favMovie(Entity(id = safeArgs.id, pictureUrl, title = ""))
                        viewModel.alreadyFav(safeArgs.id)
                    }
                }
            }
        })
    }
}