package com.example.courseskoinapp.utils

import android.util.Patterns

/**
 * @author Muhamed Amin Hassan on 04,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */

sealed class RegisterValidation {
    data object Success : RegisterValidation()
    data class Failed(val message: String) : RegisterValidation()
}

data class RegisterFieldState(
    val email: RegisterValidation, val password: RegisterValidation
)


fun validateEmail(email: String): RegisterValidation {
    if (email.isEmpty()) return RegisterValidation.Failed("Email cannot be Empty!")
    if (!(Patterns.EMAIL_ADDRESS.matcher(email)
            .matches())
    ) return RegisterValidation.Failed("Wrong Email Format!")
    return RegisterValidation.Success
}

fun validatePassword(password: String): RegisterValidation {
    if (password.isEmpty()) return RegisterValidation.Failed("Password cannot be Empty!")
    if (password.length < 6) return RegisterValidation.Failed("Password must be at least 6 numbers!")
    return RegisterValidation.Success
}