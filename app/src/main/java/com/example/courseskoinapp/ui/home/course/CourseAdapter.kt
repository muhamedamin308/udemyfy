package com.example.courseskoinapp.ui.home.course

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.courseskoinapp.data.model.Release
import com.example.courseskoinapp.databinding.ItemCourseLayoutBinding

/**
 * @author Muhamed Amin Hassan on 05,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class CourseAdapter :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    inner class CourseViewHolder(private val binding: ItemCourseLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Release) {
            binding.apply {
                tvCourseName.text = course.name
                tvCourseOrg.text = course.organization
                tvCourseStatus.text = course.status
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Release>() {
        override fun areItemsTheSame(oldItem: Release, newItem: Release): Boolean {
            Log.d("DiffUtil", "Comparing items: ${oldItem.name} vs ${newItem.name}")
            return oldItem.name.hashCode() == newItem.name.hashCode()
        }

        override fun areContentsTheSame(oldItem: Release, newItem: Release): Boolean {
            Log.d("DiffUtil", "Comparing contents: $oldItem vs $newItem")
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder =
        CourseViewHolder(
            ItemCourseLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = differ.currentList[position]
        holder.bind(course)
        holder.itemView.setOnClickListener { onCourseClicked?.invoke(course) }
    }

    var onCourseClicked: ((Release?) -> Unit)? = null
}