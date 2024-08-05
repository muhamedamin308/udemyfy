package com.example.courseskoinapp.ui.home.course.process

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.courseskoinapp.databinding.ItemOneWordLayoutBinding

/**
 * @author Muhamed Amin Hassan on 05,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class CourseOneWordAdapter :
    RecyclerView.Adapter<CourseOneWordAdapter.OneWordViewHolder>() {
    inner class OneWordViewHolder(private val binding: ItemOneWordLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(oneWord: String) {
            binding.tvTheWord.text = oneWord
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneWordViewHolder =
        OneWordViewHolder(
            ItemOneWordLayoutBinding.inflate(
                android.view.LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: OneWordViewHolder, position: Int) {
        val oneWord = differ.currentList[position]
        holder.bind(oneWord)
    }
}