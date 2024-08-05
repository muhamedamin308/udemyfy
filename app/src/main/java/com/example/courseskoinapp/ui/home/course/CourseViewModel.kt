package com.example.courseskoinapp.ui.home.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseskoinapp.data.model.Release
import com.example.courseskoinapp.data.services.FirebaseAuthServices
import com.example.courseskoinapp.repo.CourseRepository
import com.example.courseskoinapp.utils.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class CourseViewModel(
    private val repository: CourseRepository,
    private val authServices: FirebaseAuthServices
) : ViewModel() {
    private val _coursesState = MutableStateFlow<State<List<Release>>>(State.Ideal())
    val coursesState = _coursesState.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName


    init {
        fetchCourses()
        fetchUserName()
    }

    private fun fetchCourses() {
        viewModelScope.launch {
            _coursesState.emit(State.Loading())
            val courses = repository.getCourses()
            if (!courses.isNullOrEmpty()) {
                _coursesState.emit(State.Success(courses))
            } else {
                _coursesState.emit(State.Error("Failed to fetch courses"))
            }
        }
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            authServices.getUserName({ name ->
                _userName.value = name
            }, {
                _userName.value = null
            })
        }
    }
}
