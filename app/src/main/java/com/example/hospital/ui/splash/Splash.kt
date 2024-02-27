package com.example.hospital.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.hospital.R
import com.example.hospital.data.Const
import com.example.hospital.databinding.FragmentSplashBinding
import com.example.hospital.utils.MySharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

class Splash : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private var animator: ValueAnimator? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        binding.progressBar.progress = 0
        animator = ValueAnimator.ofInt(0, 100).apply {
            duration = 3000
            addUpdateListener { animation ->
                binding.progressBar.progress = animation.animatedValue as Int
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    if (isAdded && view != null) {
                        val navController = Navigation.findNavController(binding.root)
                        when {
                            MySharedPreferences.getUserType() == Const.HR -> navController.navigate(R.id.action_FSplash_to_HRHome)
                            MySharedPreferences.getUserType() == Const.RECEPTIONIST -> navController.navigate(R.id.action_FSplash_to_receptionFragment)
                            else -> navController.navigate(R.id.action_FSplash_to_FLogin)
                        }
                    }

                }
            })
        }
        animator?.start()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animator?.cancel()
        _binding = null
    }
}