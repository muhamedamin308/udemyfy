package com.example.courseskoinapp.ui.auth.signup

import androidx.lifecycle.ViewModel
import com.example.courseskoinapp.data.model.User
import com.example.courseskoinapp.data.source.services.FirebaseAuthServices
import com.example.courseskoinapp.utils.RegisterFieldState
import com.example.courseskoinapp.utils.RegisterValidation
import com.example.courseskoinapp.utils.State
import com.example.courseskoinapp.utils.validateEmail
import com.example.courseskoinapp.utils.validatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class SignupViewModel(
    private val authServices: FirebaseAuthServices
) : ViewModel() {
    private val _signupState =
        MutableStateFlow<State<User>>(State.Ideal())
    val signupState = _signupState.asSharedFlow()
    private val _registerValidate = Channel<RegisterFieldState>()
    val registerValidate = _registerValidate.receiveAsFlow()

    fun createAccount(user: User, password: String) {
        val isUserValid = validateUser(user, password)
        if (isUserValid) {
            runBlocking { _signupState.emit(State.Loading()) }
            authServices.createAccount(user, password, onSuccess = {
                _signupState.value = State.Success(user)
            }, onFailure = {
                _signupState.value = State.Error(it.message.toString())
            })
        } else {
            val registerFieldState = RegisterFieldState(
                validateEmail(user.email), validatePassword(password)
            )
            runBlocking { _registerValidate.send(registerFieldState) }
        }
    }

    private fun validateUser(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidate = validatePassword(password)
        return emailValidation is RegisterValidation.Success
                && passwordValidate is RegisterValidation.Success
    }
}




