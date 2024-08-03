package com.example.courseskoinapp.ui.auth.onboard.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.courseskoinapp.R
import com.example.courseskoinapp.databinding.FirstFragmentBinding

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class FirstScreenFragment : Fragment() {
    private lateinit var binding: FirstFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FirstFragmentBinding.inflate(inflater)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewpager)
        binding.tvNextOnboard.setOnClickListener {
            viewPager?.currentItem = 1
        }
        return binding.root
    }
}