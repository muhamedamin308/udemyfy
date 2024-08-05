package com.example.courseskoinapp.repo

import com.example.courseskoinapp.data.model.Release
import com.example.courseskoinapp.data.services.CourseApiService

/**
 * @author Muhamed Amin Hassan on 05,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class CourseRepositoryImpl(
    private val courseApi: CourseApiService
) : CourseRepository {
    override suspend fun getCourses(): List<Release>? =
        try {
            courseApi.getCourses()?.releases
        } catch (e: Exception) {
            null
        }

    override suspend fun getCourseLanguages(): List<String>? =
        try {
            courseApi.getCourses()?.releases?.flatMap { it.languages }
        } catch (e: Exception) {
            null
        }

    override suspend fun getCourseTags(): List<String>? =
        try {
            courseApi.getCourses()?.releases?.flatMap { it.tags }
        } catch (e: Exception) {
            null
        }
}