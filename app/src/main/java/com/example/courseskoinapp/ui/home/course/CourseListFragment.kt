package com.example.courseskoinapp.ui.home.course

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseskoinapp.databinding.CourseListFragmentBinding
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
class CourseListFragment : Fragment() {
    private lateinit var binding: CourseListFragmentBinding
    private val viewModel: CourseViewModel by viewModel()
    private val adapter by lazy { CourseAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CourseListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()

        adapter.onCourseClicked = { course ->
            course?.let {
                val action =
                    CourseListFragmentDirections.actionCourseListFragmentToCourseDetailFragment(it)
                findNavController().navigate(action)
            } ?: run {
                Log.e("CourseListFragment", "Clicked course is null")
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.coursesState.collectLatest {
                when (it) {
                    is State.Error -> binding.progressBarCourses.show()
                    is State.Ideal -> Unit
                    is State.Loading -> binding.progressBarCourses.show()
                    is State.Success -> {
                        binding.progressBarCourses.gone()
                        adapter.differ.submitList(it.data)
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.userName.collectLatest {
                binding.tvStudentName.text = it ?: ""
            }
        }
    }

    private fun setUpRecycler() {
        binding.recyclerCourses.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@CourseListFragment.adapter
        }
    }

    override fun onResume() {
        super.onResume()
        visibleNavigation()
    }

}