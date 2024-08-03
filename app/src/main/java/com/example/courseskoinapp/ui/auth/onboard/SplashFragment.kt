package com.example.courseskoinapp.ui.auth.onboard

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.courseskoinapp.R
import com.example.courseskoinapp.databinding.SplashFragmentBinding

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class SplashFragment : Fragment() {
    private lateinit var binding: SplashFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater)
        Handler().postDelayed({
            if (onBoardingFinished())
                findNavController().navigate(R.id.action_splashFragment_to_signupFragment)
            else
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
        }, 4000)
        return binding.root
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}