package com.example.courseskoinapp.ui.auth.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.courseskoinapp.databinding.OnboardingFragmentBinding
import com.example.courseskoinapp.ui.auth.onboard.screens.FirstScreenFragment
import com.example.courseskoinapp.ui.auth.onboard.screens.SecondScreenFragment
import com.example.courseskoinapp.ui.auth.onboard.screens.ThirdScreenFragment

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class ViewPagerFragment : Fragment() {
    private lateinit var binding: OnboardingFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingFragmentBinding.inflate(inflater)
        val fragmentList = arrayListOf(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewpager.adapter = adapter
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.circleIndicator.setViewPager(binding.viewpager)
        adapter.registerAdapterDataObserver(binding.circleIndicator.adapterDataObserver)

        return binding.root
    }
}