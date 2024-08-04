package com.example.courseskoinapp.ui.auth.onboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.courseskoinapp.R
import com.example.courseskoinapp.databinding.SplashFragmentBinding
import com.example.courseskoinapp.ui.home.main.MainActivity
import com.example.courseskoinapp.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */

class SplashFragment : Fragment() {
    private lateinit var binding: SplashFragmentBinding
    private val onboardViewModel: OnBoardingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use lifecycleScope to launch a coroutine
        lifecycleScope.launch {
            // Replace the below delay with your own logic to check the user state
            // For demonstration, using a delay of 4 seconds
            delay(4000)
            onboardViewModel.stateFlow.collect { state ->
                when (state) {
                    Constants.SIGNUP_ACCOUNT -> {
                        findNavController().navigate(state)
                    }

                    Constants.HOME_ACTIVITY -> {
                        Intent(requireActivity(), MainActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }

                    else -> findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                }
            }
        }
    }
}
