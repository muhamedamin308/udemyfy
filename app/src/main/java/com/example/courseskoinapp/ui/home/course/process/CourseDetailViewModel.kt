package com.example.courseskoinapp.ui.home.course.process

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseskoinapp.data.model.Release
import com.example.courseskoinapp.data.services.FirebaseDatabaseService
import com.example.courseskoinapp.utils.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author Muhamed Amin Hassan on 08,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class CourseDetailViewModel(
    private val databaseService: FirebaseDatabaseService
) : ViewModel() {
    private val _watchLaterCourse = MutableStateFlow<State<Release>>(State.Ideal())
    val watchLaterCourse = _watchLaterCourse.asStateFlow()

    fun addCourseToWatchLater(course: Release) {
        viewModelScope.launch { _watchLaterCourse.emit(State.Loading()) }
        databaseService.addToWatchLater(course) { courseRelease, exception ->
            viewModelScope.launch {
                if (exception == null)
                    _watchLaterCourse.emit(State.Success(courseRelease!!))
                else
                    _watchLaterCourse.emit(State.Error(exception.message.toString()))
            }
        }
    }
}