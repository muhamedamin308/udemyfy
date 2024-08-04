package com.example.courseskoinapp.ui.auth.signup

import androidx.lifecycle.ViewModel
import com.example.courseskoinapp.data.model.User
import com.example.courseskoinapp.utils.Constants
import com.example.courseskoinapp.utils.RegisterFieldState
import com.example.courseskoinapp.utils.RegisterValidation
import com.example.courseskoinapp.utils.State
import com.example.courseskoinapp.utils.validateEmail
import com.example.courseskoinapp.utils.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore
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
            auth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    it?.user?.let { firebaseUser ->
                        saveUserDetails(firebaseUser.uid, user)
                    }
                }.addOnFailureListener {
                    _signupState.value = State.Error(it.message.toString())
                }
        } else {
            val registerFieldState = RegisterFieldState(
                validateEmail(user.email), validatePassword(password)
            )
            runBlocking { _registerValidate.send(registerFieldState) }
        }
    }


    private fun saveUserDetails(userId: String, user: User) {
        store.collection(Constants.Collections.USER_COLLECTION).document(userId).set(user)
            .addOnSuccessListener {
                _signupState.value = State.Success(user)
            }.addOnFailureListener {
                _signupState.value = State.Error(it.message.toString())
            }
    }

    private fun validateUser(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidate = validatePassword(password)
        return emailValidation is RegisterValidation.Success
                && passwordValidate is RegisterValidation.Success
    }
}