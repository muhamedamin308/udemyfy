package com.example.courseskoinapp.ui.auth.onboard.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.courseskoinapp.R
import com.example.courseskoinapp.databinding.ThirdFragmentBinding
import com.example.courseskoinapp.ui.auth.onboard.OnBoardingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class ThirdScreenFragment : Fragment() {
    private lateinit var binding: ThirdFragmentBinding
    private val onboardViewModel: OnBoardingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ThirdFragmentBinding.inflate(inflater)

        binding.btnStart.setOnClickListener {
            onboardViewModel.activateGetStarted()
            findNavController().navigate(R.id.action_viewPagerFragment_to_signupFragment)
        }

        return binding.root
    }
}
