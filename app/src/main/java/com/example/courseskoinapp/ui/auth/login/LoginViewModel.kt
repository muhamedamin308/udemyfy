package com.example.courseskoinapp.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseskoinapp.data.source.services.FirebaseAuthServices
import com.example.courseskoinapp.utils.State
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class LoginViewModel(
    private val authServices: FirebaseAuthServices
) : ViewModel() {
    private val _loginState = MutableSharedFlow<State<FirebaseUser>>()
    val loginState = _loginState.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch { _loginState.emit(State.Loading()) }
        authServices.login(email, password, onSuccess = {
            viewModelScope.launch {
                _loginState.emit(State.Success(it))
            }
        }, onFailure = {
            viewModelScope.launch {
                _loginState.emit(State.Error(it.message.toString()))
            }
        })
    }
}