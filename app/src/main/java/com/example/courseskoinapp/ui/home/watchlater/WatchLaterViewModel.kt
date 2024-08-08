package com.example.courseskoinapp.ui.home.watchlater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseskoinapp.data.model.Release
import com.example.courseskoinapp.data.services.FirebaseAuthServices
import com.example.courseskoinapp.data.services.FirebaseDatabaseService
import com.example.courseskoinapp.utils.State
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class WatchLaterViewModel(
    private val databaseService: FirebaseDatabaseService,
    private val authServices: FirebaseAuthServices
) : ViewModel() {

    private val _watchLaterCourse =
        MutableStateFlow<State<List<Release>>>(State.Ideal())
    val watchLaterCourse = _watchLaterCourse.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    private var coursesDocument = emptyList<DocumentSnapshot>()

    init {
        fetchWatchLaterCourses()
        fetchUserName()
    }

    fun removeCourseFromWatchLater(course: Release) {
        val courseIndex = watchLaterCourse.value.data?.indexOf(course)
        databaseService.deleteFromWatchLater(courseIndex, coursesDocument)
    }

    private fun fetchWatchLaterCourses() {
        viewModelScope.launch { _watchLaterCourse.emit(State.Loading()) }
        databaseService.getWatchLaterCourses { courses, documents, exception ->
            viewModelScope.launch {
                if (exception == null && courses != null && documents != null) {
                    coursesDocument = documents
                    _watchLaterCourse.emit(State.Success(courses))
                } else {
                    _watchLaterCourse.emit(State.Error(exception?.message.toString()))
                }
            }
        }
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            authServices.getUserProfile { user, exception ->
                if (exception == null)
                    _userName.value = user?.name
                else
                    _userName.value = null
            }
        }
    }
}