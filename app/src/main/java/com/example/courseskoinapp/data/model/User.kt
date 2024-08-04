package com.example.courseskoinapp.data.model

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */

data class User(
    val name: String,
    val email: String,
    val password: String? = null,
    val profilePhotoUrl: Int?,
    val courses: List<Course>? = null
)