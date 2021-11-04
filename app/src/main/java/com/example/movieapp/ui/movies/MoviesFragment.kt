package com.example.movieapp.ui.movies


import android.app.Dialog
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.ui.adapters.MoviesRecyclerAdapter
import com.example.movieapp.api.ApiErrorHandling
import com.example.movieapp.ui.base_fragment.BaseFragment
import com.example.movieapp.databinding.DialogBinding
import com.example.movieapp.databinding.MoviesFragmentBinding
import com.example.movieapp.extensions.animation
import com.example.movieapp.extensions.observer
import com.example.movieapp.extensions.setUp
import com.example.movieapp.extensions.toast
import com.example.movieapp.ui.listeners.SetScrollListener
import com.example.movieapp.utils.Constants.CONNECTION_CHECKER_DELAY
import com.example.movieapp.utils.Constants.POPULAR
import com.example.movieapp.utils.Constants.POPULAR_FOR_VIEW
import com.example.movieapp.utils.Constants.SAVED
import com.example.movieapp.utils.Constants.TOP_RATED
import com.example.movieapp.utils.Constants.TOP_RATED_FOR_VIEW
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviesFragment : BaseFragment<MoviesFragmentBinding, MoviesFragmentViewModel>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> MoviesFragmentBinding get() = MoviesFragmentBinding::inflate
    override fun getViewModel() = MoviesFragmentViewModel::class.java
    private lateinit var moviesRecyclerAdapter : MoviesRecyclerAdapter
    override fun initFragment(viewModel: MoviesFragmentViewModel) {
        observe(viewModel)
        initRecyclerView(viewModel)
        favoriteMovies(viewModel)
        networkConnectionObserver(viewModel)
    }

    private fun errorDialog(){
        val viewDialog = Dialog(requireContext())
        val bindDialog = DialogBinding.inflate(layoutInflater)
        viewDialog.setUp(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.MATCH_PARENT, bindDialog)
        bindDialog.CloseButton.setOnClickListener {
            viewDialog.cancel()
        }
        viewDialog.show()
    }

    private fun networkConnectionObserver(viewModel: MoviesFragmentViewModel) {
        viewModel.checkConnection.observe(viewLifecycleOwner, {
            viewModel.setInternetConnection(it)
            connectionHandler(viewModel)
        })
        viewLifecycleOwner.lifecycleScope.launch {
            delay(CONNECTION_CHECKER_DELAY)
            if (viewModel.isNetworkAvailable.value == null) {
                viewModel.setInternetConnection(false)
                connectionHandler(viewModel)
            }
        }
    }

    private fun connectionHandler(ViewModel: MoviesFragmentViewModel){
        viewLifecycleOwner.lifecycleScope.launch{
            observer(ViewModel.isNetworkAvailable){
                if(it == true){
                    ViewModel.getData()
                }else if (it == false){
                    errorDialog()
                }else{
                    networkConnectionObserver(ViewModel)
                }
            }
        }
    }


    private fun initRecyclerView(ViewModel: MoviesFragmentViewModel) {
        moviesRecyclerAdapter = MoviesRecyclerAdapter()
        with(binding.recycler){
            animation(R.anim.logo_animation)
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = moviesRecyclerAdapter
            addOnScrollListener(SetScrollListener{ViewModel.loadNextPage()})
        }
        moviesRecyclerAdapter.loadPage= {
            ViewModel.newPage = it
        }
        moviesRecyclerAdapter.getMoviesId = { id  ->
            val action =
                MoviesFragmentDirections.actionMoviesToMoviesDetailScreen(
                    id
                )
            findNavController().navigate(action)
        }
    }
    private fun observe(ViewModel: MoviesFragmentViewModel){
        observer(ViewModel.livedata){
            when(it.status){
                ApiErrorHandling.Status.Success ->{
                    if(it.data!!.page != 1){
                        it.data.let { it1 -> moviesRecyclerAdapter.loadNextPage(it1) }
                    }else{
                        it.data.let { it1 -> moviesRecyclerAdapter.setData(it1) }
                    }

                }
                ApiErrorHandling.Status.Error ->{
                    it.message?.toast(requireContext())
                    it.message?.let { it1 -> d("error", it1) }
                }
            }
        }
        observer(ViewModel.savedLiveData){
            moviesRecyclerAdapter.setSavedData(it)
        }
        observer(ViewModel.isTopRated){ isTopRated ->
            binding.button.setOnClickListener{
                connectionHandler(ViewModel)
                binding.recycler.animation(R.anim.logo_animation)
                if (isTopRated){
                    binding.button.text = POPULAR_FOR_VIEW
                    ViewModel.category = POPULAR
                    ViewModel.getData()
                }else{
                    binding.button.text = TOP_RATED_FOR_VIEW
                    ViewModel.category = TOP_RATED
                    ViewModel.getData()
                }
            }
        }
    }
    private fun favoriteMovies(ViewModel: MoviesFragmentViewModel){
        binding.favoriteImage.setOnClickListener{
            binding.recycler.animation(R.anim.logo_animation)
            ViewModel.category = SAVED
            ViewModel.getData()
        }
    }
}