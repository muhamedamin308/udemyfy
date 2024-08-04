package com.example.courseskoinapp.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseskoinapp.utils.State
import com.google.firebase.auth.FirebaseAuth
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
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _loginState = MutableSharedFlow<State<FirebaseUser>>()
    val loginState = _loginState.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch { _loginState.emit(State.Loading()) }
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            viewModelScope.launch {
                it?.user?.let { user ->
                    _loginState.emit(State.Success(user))
                }
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _loginState.emit(State.Error(it.message.toString()))
            }
        }
    }
}