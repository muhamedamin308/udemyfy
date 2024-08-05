package com.example.courseskoinapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */

@Parcelize
data class User(
    val name: String = "",
    val email: String = "",
    val password: String? = null,
    val profilePhotoUrl: Int? = null,
    val courses: @RawValue List<Course>? = null
) : Parcelable
