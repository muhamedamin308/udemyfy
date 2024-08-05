package com.example.courseskoinapp.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.courseskoinapp.databinding.ProfileFragmentBinding
import com.example.courseskoinapp.utils.visibleNavigation

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        visibleNavigation()
    }
}