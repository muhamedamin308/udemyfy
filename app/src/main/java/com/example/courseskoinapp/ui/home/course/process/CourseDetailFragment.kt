package com.example.courseskoinapp.ui.home.course.process

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseskoinapp.databinding.CourseDetailFragmentBinding
import com.example.courseskoinapp.utils.gone
import com.example.courseskoinapp.utils.invisibleNavigation
import com.example.courseskoinapp.utils.show

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class CourseDetailFragment : Fragment() {
    private lateinit var binding: CourseDetailFragmentBinding
    private val args: CourseDetailFragmentArgs by navArgs()
    private val tagsAdapter by lazy { CourseOneWordAdapter() }
    private val languagesAdapter by lazy { CourseOneWordAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        invisibleNavigation()
        binding = CourseDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val course = args.course
        setUpLanguagesRecycler()
        setUpTagsRecycler()
        binding.apply {
            course?.let {
                arrowBack.setOnClickListener { findNavController().navigateUp() }
                tvCourseName.text = course.name
                tvCourseDescription.text = course.description
                imageSendEmail.setOnClickListener {
                    course.contact.email?.let { it1 -> sendEmail(it1) }
                }
                imageGithubRepo.setOnClickListener {
                    if (course.repositoryURL.isNotEmpty())
                        openHomepage(course.repositoryURL)
                    else Toast.makeText(
                        requireContext(),
                        "No github repo available",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                imageHomePage.setOnClickListener {
                    if (course.homepageURL.isNotEmpty())
                        openHomepage(course.homepageURL)
                    else Toast.makeText(
                        requireContext(),
                        "No homepage available",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                imageLicenses.setOnClickListener {
                    if (course.permissions.licenses.isNotEmpty())
                        openLicences(course.permissions.licenses[0].URL)
                    else Toast.makeText(
                        requireContext(),
                        "No licenses available",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                tvCreatedDate.text = course.date.created ?: "N/A"
                tvLastModifiedDate.text = course.date.lastModified ?: "N/A"
            } ?: run { Log.e("CourseDetailFragment", "Course argument is null") }
        }
        course?.let {
            course.languages.let {
                if (it.isNotEmpty()) {
                    binding.tvLangNothing.gone()
                    languagesAdapter.differ.submitList(it)
                } else binding.tvLangNothing.show()
            }
            course.tags.let {
                if (it.isNotEmpty()) {
                    binding.tvTagsNothing.gone()
                    tagsAdapter.differ.submitList(it)
                } else binding.tvTagsNothing.show()
            }
        }
    }

    private fun sendEmail(email: String) {
        if (email.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, "Udemyfy Application Request")
            }
            try {
                if (intent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(Intent.createChooser(intent, "Choose email client"))
                } else {
                    Toast.makeText(
                        requireContext(),
                        "No email client installed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error occurred while trying to send an email",
                    Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Email address is empty",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun openGithub(githubLink: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubLink))
        startActivity(intent)
    }

    private fun openHomepage(homepage: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(homepage))
        startActivity(intent)
    }

    private fun openLicences(githubLicense: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubLicense))
        startActivity(intent)
    }

    private fun setUpTagsRecycler() {
        binding.recyclerTags.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = tagsAdapter
        }
    }

    private fun setUpLanguagesRecycler() {
        binding.recyclerCourseLanguages.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = languagesAdapter
        }
    }
}