package com.example.movieapp.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.base_fragment.BaseFragment
import com.example.movieapp.databinding.FragmentSplashScreenBinding
import com.example.movieapp.extensions.animation
import com.example.movieapp.utils.Constants.SPLASH_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreen : BaseFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {
    override fun init(layoutInflater: LayoutInflater, viewGroup: ViewGroup?){
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