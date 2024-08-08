package com.example.courseskoinapp.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseskoinapp.data.model.User
import com.example.courseskoinapp.data.services.FirebaseAuthServices
import com.example.courseskoinapp.utils.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class ProfileViewModel(
    private val authServices: FirebaseAuthServices
) : ViewModel() {
    private val _user = MutableStateFlow<State<User>>(State.Ideal())
    val user = _user.asStateFlow()

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch { _user.emit(State.Loading()) }
        authServices.getUserProfile { user, exception ->
            if (exception == null)
                viewModelScope.launch { _user.emit(State.Success(user!!)) }
            else
                viewModelScope.launch { _user.emit(State.Error(exception.message.toString())) }
        }
    }

    fun signOut() = authServices.logout()
}