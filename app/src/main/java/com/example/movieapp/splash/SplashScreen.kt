package com.example.movieapp.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.base_fragment.BaseFragment
import com.example.movieapp.databinding.FragmentSplashScreenBinding
import com.example.movieapp.utils.Constants.SPLASH_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreen : BaseFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {
    override fun init(layoutInflater: LayoutInflater, viewGroup: ViewGroup?){
        val topAnimation = AnimationUtils.loadAnimation(context, R.anim.top_animation)
        val middleAnimation = AnimationUtils.loadAnimation(context, R.anim.mid_animation)
        val bottomAnimations = AnimationUtils.loadAnimation(context, R.anim.bottom_animation)
        val logoAnimation = AnimationUtils.loadAnimation(context, R.anim.logo_animation)
        binding.topText.startAnimation(topAnimation)
        binding.midText.startAnimation(middleAnimation)
        binding.botText.startAnimation(bottomAnimations)
        binding.logo.startAnimation(logoAnimation)
        lifecycleScope.launch {
            delay(SPLASH_DELAY)
            findNavController().navigate(R.id.action_splashScreen2_to_movies)
        }
    }

}