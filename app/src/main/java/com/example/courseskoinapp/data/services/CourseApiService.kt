package com.example.courseskoinapp.data.services

import com.example.courseskoinapp.data.model.Course
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
interface CourseApiService {
    @GET("code.json")
    suspend fun getCourses(): Course?
}