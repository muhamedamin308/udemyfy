package com.example.courseskoinapp.ui.home.watchlater

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseskoinapp.databinding.WatchlaterFragmentBinding
import com.example.courseskoinapp.ui.home.course.CourseAdapter
import com.example.courseskoinapp.utils.State
import com.example.courseskoinapp.utils.gone
import com.example.courseskoinapp.utils.show
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class WatchLaterFragment : Fragment() {
    private lateinit var binding: WatchlaterFragmentBinding
    private val viewModel by viewModel<WatchLaterViewModel>()
    private val courseAdapter by lazy { CourseAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WatchlaterFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.arrowBack.setOnClickListener { findNavController().navigateUp() }

        courseAdapter.onCourseClicked = { course ->
            course?.let {
                val action =
                    WatchLaterFragmentDirections.actionWatchLaterFragmentToCourseDetailFragment(
                        course
                    )
                findNavController().navigate(action)
            } ?: run {
                Log.e("WatchLaterFragment", "Clicked course is null")
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.watchLaterCourse.collectLatest {
                when (it) {
                    is State.Error -> binding.progressBarCourses.show()
                    is State.Ideal -> Unit
                    is State.Loading -> binding.progressBarCourses.show()
                    is State.Success -> {
                        binding.progressBarCourses.gone()
                        courseAdapter.differ.submitList(it.data)
                        if (it.data!!.isEmpty())
                            binding.emptyWatchLater.show()
                        else
                            binding.emptyWatchLater.gone()
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.userName.collectLatest {
                binding.tvStudentName.text = it ?: ""
            }
        }

        // Setup swipe to delete
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val course = courseAdapter.differ.currentList[position]
                viewModel.removeCourseFromWatchLater(course)
                Toast.makeText(requireContext(), "${course.name} deleted", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerWatchLater)
    }

    private fun initRecyclerView() {
        binding.recyclerWatchLater.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = courseAdapter
        }
    }
}
