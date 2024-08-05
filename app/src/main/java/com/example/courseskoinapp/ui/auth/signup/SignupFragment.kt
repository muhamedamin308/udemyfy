package com.example.courseskoinapp.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.courseskoinapp.R
import com.example.courseskoinapp.data.model.User
import com.example.courseskoinapp.databinding.SignupFragmentBinding
import com.example.courseskoinapp.utils.Handlers
import com.example.courseskoinapp.utils.RegisterValidation
import com.example.courseskoinapp.utils.State
import com.example.courseskoinapp.utils.gone
import com.example.courseskoinapp.utils.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class SignupFragment : Fragment() {
    private lateinit var binding: SignupFragmentBinding
    private val signupViewModel: SignupViewModel by viewModel()
    private var randomImage: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignupFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvHaveAccount.setOnClickListener {
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            }


            btnLogin.setOnClickListener {
                val password = etPassword.text.toString().trim()
                val user = User(
                    name = etName.text.toString().trim(),
                    email = etEmail.text.toString().trim(),
                    password = password,
                    profilePhotoUrl = randomImage
                )
                signupViewModel.createAccount(user, password)
            }
        }

        lifecycleScope.launchWhenStarted {
            signupViewModel.randomImage.collect {
                randomImage = it
            }
        }

        lifecycleScope.launchWhenStarted {
            signupViewModel.registerValidate.collectLatest { validation ->
                if (validation.email is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.etEmail.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }
                if (validation.password is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.etPassword.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            signupViewModel.signupState.collect {
                when (it) {
                    is State.Success -> {
                        binding.signupProgressBar.show()
                        findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                    }

                    is State.Error -> {
                        binding.signupProgressBar.gone()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> binding.signupProgressBar.gone()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onPause() {
        super.onPause()
        // Reset to default or any desired mode when fragment is paused
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }
}