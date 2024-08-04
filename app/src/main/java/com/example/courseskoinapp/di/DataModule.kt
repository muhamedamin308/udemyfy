package com.example.courseskoinapp.di

import android.app.Application
import android.content.SharedPreferences
import com.example.courseskoinapp.ui.auth.login.LoginViewModel
import com.example.courseskoinapp.ui.auth.onboard.OnBoardingViewModel
import com.example.courseskoinapp.ui.auth.signup.SignupViewModel
import com.example.courseskoinapp.utils.Constants
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { Firebase.firestore }
    single<SharedPreferences> {
        val application: Application = get()
        application.getSharedPreferences(
            Constants.SHARED_PREFERENCES_NAME,
            Application.MODE_PRIVATE
        )
    }
    viewModel { OnBoardingViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get(), get()) }
}