package com.example.movieapp.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSplashScreenBinding
import com.example.movieapp.extensions.animation
import com.example.movieapp.utils.Constants.SPLASH_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreen : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    private fun initFragment(){
        with(binding){
            topText.animation(R.anim.top_animation)
            midText.animation(R.anim.mid_animation)
            botText.animation(R.anim.bottom_animation)
            logo.animation(R.anim.logo_animation)
        }

        lifecycleScope.launch {
            delay(SPLASH_DELAY)
            findNavController().navigate(R.id.action_splashScreen2_to_movies)
        }
    }
}
