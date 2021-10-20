package com.example.movieapp.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapters.HomeRecyclerAdapter
import com.example.movieapp.api.ApiErrorHandling
import com.example.movieapp.base_fragment.BaseFragment
import com.example.movieapp.databinding.MoviesFragmentBinding
import com.example.movieapp.extensions.animation
import com.example.movieapp.utils.Constants.POPULAR
import com.example.movieapp.utils.Constants.TOP_RATED
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates


@AndroidEntryPoint
class Movies : BaseFragment<MoviesFragmentBinding>(MoviesFragmentBinding::inflate) {

    private val viewModel: MoviesViewModel by viewModels()
    private var isPopular by Delegates.notNull<Boolean>()
    private lateinit var homeRecyclerAdapter : HomeRecyclerAdapter
    override fun init(layoutInflater: LayoutInflater, viewGroup: ViewGroup?) {
        viewModel.getData()
        initFragment()
    }

    private fun initFragment(){
        observe()
        initRecyclerView()
        changeCategoryButtonLogic()

    }


    private fun initRecyclerView() {
        homeRecyclerAdapter = HomeRecyclerAdapter()
        with(binding){
            recycler.animation(R.anim.logo_animation)
            recycler.layoutManager = GridLayoutManager(requireContext(),2)
            recycler.adapter = homeRecyclerAdapter
        }

    }

    private fun observe(){
        viewModel.livedata.observe(viewLifecycleOwner, {
            when(it.status){
                ApiErrorHandling.Status.Success ->{
                    it.data?.let {
                            it1 -> homeRecyclerAdapter.getData(it1)
                    }
                }

                ApiErrorHandling.Status.Error ->{
                    Toast.makeText(requireActivity(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun changeCategoryButtonLogic(){
        isPopular = false
        binding.button.setOnClickListener{
            binding.recycler.animation(R.anim.logo_animation)
            isPopular = if (isPopular){
                binding.button.text = "Popular"
                viewModel.category = POPULAR
                viewModel.getData()
                false
            }else{
                binding.button.text = "Top Rated"
                viewModel.category = TOP_RATED
                viewModel.getData()
                true
            }
        }

    }

}