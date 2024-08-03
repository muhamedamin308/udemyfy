package com.example.courseskoinapp.ui.auth.onboard.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.courseskoinapp.R
import com.example.courseskoinapp.databinding.SecondFragmentBinding

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class SecondScreenFragment : Fragment() {
    private lateinit var binding: SecondFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SecondFragmentBinding.inflate(inflater)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewpager)
        binding.tvNextOnboard.setOnClickListener {
            viewPager?.currentItem = 2
        }
        return binding.root
    }
}