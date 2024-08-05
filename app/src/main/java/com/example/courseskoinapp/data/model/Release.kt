package com.example.courseskoinapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Release(
    val contact: @RawValue Contact = Contact(), // Ensure proper initialization
    val date: @RawValue Date = Date(),
    val description: String = "",
    val disclaimerURL: String = "",
    val downloadURL: String = "",
    val homepageURL: String = "",
    val laborHours: Int = 0,
    val languages: List<String> = emptyList(),
    val name: String = "",
    val organization: String = "",
    val partners:@RawValue List<Any> = emptyList(),
    val permissions:@RawValue Permissions = Permissions(),
    val relatedCode:@RawValue List<RelatedCode> = emptyList(),
    val repositoryURL: String = "",
    val status: String = "",
    val tags: List<String> = emptyList(),
    val vcs: String = ""
) : Parcelable

