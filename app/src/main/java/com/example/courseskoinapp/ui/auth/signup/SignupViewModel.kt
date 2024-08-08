package com.example.courseskoinapp.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseskoinapp.data.model.User
import com.example.courseskoinapp.data.services.FirebaseAuthServices
import com.example.courseskoinapp.utils.RegisterFieldState
import com.example.courseskoinapp.utils.RegisterValidation
import com.example.courseskoinapp.utils.State
import com.example.courseskoinapp.utils.validateEmail
import com.example.courseskoinapp.utils.validatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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
    private val _randomImage = MutableStateFlow<String?>(null)
    val randomImage: StateFlow<String?> = _randomImage

    init {
        viewModelScope.launch {
            _randomImage.value = generateRandomImage()
        }
    }


    fun createAccount(user: User, password: String) {
        val isUserValid = validateUser(user, password)
        if (isUserValid) {
            runBlocking { _signupState.emit(State.Loading()) }
            authServices.createAccount(user, password) { newUser, exception ->
                if (exception == null)
                    _signupState.value = State.Success(newUser!!)
                else
                    _signupState.value = State.Error(exception.message.toString())
            }
        } else {
            val registerFieldState = RegisterFieldState(
                validateEmail(user.email), validatePassword(password)
            )
            runBlocking { _registerValidate.send(registerFieldState) }
        }
    }

    private suspend fun generateRandomImage(): String? {
        return try {
            authServices.generateRandomProfilePicture()
        } catch (e: Exception) {
            ""
        }
    }

    private fun validateUser(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidate = validatePassword(password)
        return emailValidation is RegisterValidation.Success
                && passwordValidate is RegisterValidation.Success
    }
}




