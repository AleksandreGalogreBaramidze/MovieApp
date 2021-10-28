package com.example.movieapp.movies


import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapters.HomeRecyclerAdapter
import com.example.movieapp.api.ApiErrorHandling
import com.example.movieapp.base_fragment.BaseFragment
import com.example.movieapp.databinding.DialogBinding
import com.example.movieapp.databinding.MoviesFragmentBinding
import com.example.movieapp.extensions.animation
import com.example.movieapp.extensions.setUp
import com.example.movieapp.extensions.toast
import com.example.movieapp.utils.Constants.POPULAR
import com.example.movieapp.utils.Constants.POPULAR_FOR_VIEW
import com.example.movieapp.utils.Constants.SAVED
import com.example.movieapp.utils.Constants.TOP_RATED
import com.example.movieapp.utils.Constants.TOP_RATED_FOR_VIEW
import com.example.movieapp.utils.InternetChecker
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates


@AndroidEntryPoint
class Movies : BaseFragment<MoviesFragmentBinding>(MoviesFragmentBinding::inflate) {

    private val viewModel: MoviesViewModel by viewModels()
    private var isPopular by Delegates.notNull<Boolean>()
    private var internetCheckerBoolean: Boolean? = null
    private lateinit var homeRecyclerAdapter : HomeRecyclerAdapter
    override fun init(layoutInflater: LayoutInflater, viewGroup: ViewGroup?) {
        observeNetworkConnection()
        viewModel.getData()
        observe()
        initRecyclerView()
        changeCategoryButtonLogic()
        favoriteMovies()
    }

    private fun internetErrorDialog(){
        val viewDialog = Dialog(requireContext())
        val bindDialog = DialogBinding.inflate(layoutInflater)
        viewDialog.setUp(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.MATCH_PARENT, bindDialog)
        bindDialog.CloseButton.setOnClickListener {
            viewDialog.cancel()
        }
        viewDialog.show()
    }

    private fun observeNetworkConnection() {
        InternetChecker(requireContext()).observe(viewLifecycleOwner, {
            internetCheckerBoolean = it
            connectionHandler()
            if (internetCheckerBoolean == null) {
                internetCheckerBoolean = false
                connectionHandler()
            }
        })
    }

    private fun connectionHandler() {
        if(internetCheckerBoolean == true){
            viewModel.getData()
        }else if (internetCheckerBoolean == false){
            internetErrorDialog()
        }else{
            observeNetworkConnection()
        }
    }

    private fun initRecyclerView() {
        homeRecyclerAdapter = HomeRecyclerAdapter()
        with(binding.recycler){
            animation(R.anim.logo_animation)
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = homeRecyclerAdapter
        }
        homeRecyclerAdapter.loadPage = {
            viewModel.loadNextPage(it)
        }
        homeRecyclerAdapter.getId = { id  ->
            val action = MoviesDirections.actionMoviesToMoviesDetailScreen(id)
            findNavController().navigate(action)
        }
    }

    private fun observe(){
        observeNetworkConnection()
        viewModel.livedata.observe(viewLifecycleOwner, {
            when(it.status){
                ApiErrorHandling.Status.Success ->{
                    it.data?.let {
                            it1 -> homeRecyclerAdapter.getData(it1)
                    }
                }
                ApiErrorHandling.Status.Error ->{
                    it.message?.toast(requireContext())
                    if(it.message == "Unable to resolve host \"api.themoviedb.org\": No address associated with hostname"){
                        internetErrorDialog()
                    }
                }
            }
        })
        viewModel.paginationLiveData.observe(viewLifecycleOwner, {
            if(viewModel.category != SAVED){
                when (it.status) {
                    ApiErrorHandling.Status.Success -> {
                        it.data?.let { it1 -> homeRecyclerAdapter.loadNextPage(it1) }
                    }
                    ApiErrorHandling.Status.Error -> {
                        it.message?.toast(requireContext())
                        if(it.message == "Unable to resolve host \"api.themoviedb.org\": No address associated with hostname"){
                            internetErrorDialog()
                        }
                    }
                }
            }


        })
        viewModel.savedLiveData.observe(viewLifecycleOwner, {
            homeRecyclerAdapter.getSavedData(it)
        })
    }


    private fun changeCategoryButtonLogic(){
        isPopular = false
        binding.button.setOnClickListener{
            binding.recycler.animation(R.anim.logo_animation)
            isPopular = if (isPopular){
                binding.button.text = POPULAR_FOR_VIEW
                viewModel.category = POPULAR
                viewModel.getData()
                false
            }else{
                binding.button.text = TOP_RATED_FOR_VIEW
                viewModel.category = TOP_RATED
                viewModel.getData()
                true
            }
        }
    }

    private fun favoriteMovies(){
        binding.favoriteImage.setOnClickListener{
            binding.recycler.animation(R.anim.logo_animation)
            viewModel.category = SAVED
            viewModel.getData()
        }
    }

}