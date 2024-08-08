package com.example.courseskoinapp.ui.home.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.courseskoinapp.R
import com.example.courseskoinapp.databinding.ProfileFragmentBinding
import com.example.courseskoinapp.ui.auth.AuthenticationActivity
import com.example.courseskoinapp.utils.State
import com.example.courseskoinapp.utils.gone
import com.example.courseskoinapp.utils.show
import com.example.courseskoinapp.utils.visibleNavigation
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private val profileViewModel by viewModel<ProfileViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        visibleNavigation()
        binding = ProfileFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnWatchLater.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_watchLaterFragment)
            }
            logout.setOnClickListener {
                profileViewModel.signOut()
                val intent = Intent(requireActivity(), AuthenticationActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        lifecycleScope.launchWhenStarted {
            profileViewModel.user.collectLatest {
                when (it) {
                    is State.Error -> {
                        binding.profileProgressBar.gone()
                        Toast.makeText(
                            requireContext(),
                            "Error: ${it.message.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is State.Ideal -> Unit
                    is State.Loading -> binding.profileProgressBar.show()
                    is State.Success -> {
                        binding.profileProgressBar.gone()
                        Glide.with(requireView())
                            .load(it.data!!.profilePhotoUrl!!)
                            .error(ColorDrawable(Color.BLACK))
                            .into(binding.imageStudentProfile)
                        binding.etStudentName.setText(it.data.name)
                        binding.etStudentEmail.setText(it.data.email)
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        visibleNavigation()
    }
}