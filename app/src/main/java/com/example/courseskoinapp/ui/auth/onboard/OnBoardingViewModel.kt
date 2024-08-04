package com.example.courseskoinapp.ui.auth.onboard

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseskoinapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author Muhamed Amin Hassan on 04,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */

class OnBoardingViewModel(
    private val sharedPreferences: SharedPreferences,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        val isStarted = sharedPreferences.getBoolean(
            Constants.SHARED_PREFERENCES_KEY,
            false
        )
        val user = auth.currentUser
        if (user != null) {
            viewModelScope.launch { _stateFlow.emit(Constants.HOME_ACTIVITY) }
        } else if (isStarted) {
            viewModelScope.launch { _stateFlow.emit(Constants.SIGNUP_ACCOUNT) }
        } else Unit
    }

    fun activateGetStarted() {
        sharedPreferences.edit().putBoolean(
            Constants.SHARED_PREFERENCES_KEY,
            true
        ).apply()
    }
}