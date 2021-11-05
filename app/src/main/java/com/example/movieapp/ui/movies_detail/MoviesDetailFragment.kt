package com.example.movieapp.ui.movies_detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.api.ApiErrorHandling
import com.example.movieapp.ui.base_fragment.BaseFragment
import com.example.movieapp.database.Entity
import com.example.movieapp.databinding.MoviesDetailFragmentBinding
import com.example.movieapp.extensions.load
import com.example.movieapp.extensions.observer
import com.example.movieapp.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesDetailFragment : BaseFragment<MoviesDetailFragmentBinding, MoviesDetailFragmentViewModel>() {
    override fun getViewModel() = MoviesDetailFragmentViewModel::class.java

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> MoviesDetailFragmentBinding get() = MoviesDetailFragmentBinding::inflate
    
    private val safeArgs: MoviesDetailFragmentArgs by navArgs()
    private lateinit var pictureUrl : String
    override fun initFragment(viewModel: MoviesDetailFragmentViewModel) {
        viewModel.movieDetails(safeArgs.id)
        viewModel.isFavorite(safeArgs.id)
        binding.backArrowIv.setOnClickListener {
            findNavController().navigateUp()
        }
        observes(viewModel)
    }
    @SuppressLint("SetTextI18n")
    private fun observes(ViewModel: MoviesDetailFragmentViewModel) {
        observer(ViewModel.liveData){
            when (it.status) {
                ApiErrorHandling.Status.Success -> {
                    pictureUrl = it.data!!.posterPath
                    with(binding) {
                        mainImage.load(requireContext(), it.data.urlGenerator())
                        detailMovieTitleTxt.text = it.data.originalTitle
                        detailMovieRatingTxt.text = it.data.voteAverage.toString()
                        detailMovieOverViewTxt.text = it.data.overview
                    }
                }
                ApiErrorHandling.Status.Error -> {
                    it.message?.toast(requireContext())
                }
            }
        }
        observer(ViewModel.checkerLiveData){
            if(it){
                with(binding.favoriteStarImageView){
                    setImageResource(R.drawable.star)
                    setOnClickListener {
                        ViewModel.removeFavorite(safeArgs.id)
                        ViewModel.isFavorite(safeArgs.id)
                    }
                }
            }else{
                with(binding.favoriteStarImageView){
                    setImageResource(android.R.drawable.star_off)
                    setOnClickListener {
                        ViewModel.favoriteMovies(Entity(id = safeArgs.id, pictureUrl, title = ""))
                        ViewModel.isFavorite(safeArgs.id)
                    }
                }
            }
        }
    }
}