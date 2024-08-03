package com.example.courseskoinapp.ui.auth.onboard.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.courseskoinapp.R
import com.example.courseskoinapp.databinding.ThirdFragmentBinding

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class ThirdScreenFragment : Fragment() {
    private lateinit var binding: ThirdFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ThirdFragmentBinding.inflate(inflater)
        binding.tvFinishOnboard.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_signupFragment)
            onBoardingFinished()
        }
        return binding.root

    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}