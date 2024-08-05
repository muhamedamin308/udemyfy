package com.example.courseskoinapp.repo

import com.example.courseskoinapp.data.model.Course
import com.example.courseskoinapp.data.model.Release

/**
 * @author Muhamed Amin Hassan on 05,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
interface CourseRepository {
    suspend fun getCourses(): List<Release>?
    suspend fun getCourseLanguages(): List<String>?
    suspend fun getCourseTags(): List<String>?
}