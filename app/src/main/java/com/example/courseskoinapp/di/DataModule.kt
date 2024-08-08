package com.example.courseskoinapp.di

import android.app.Application
import android.content.SharedPreferences
import com.example.courseskoinapp.data.services.CourseApiService
import com.example.courseskoinapp.data.services.FirebaseAuthServices
import com.example.courseskoinapp.data.services.FirebaseDatabaseService
import com.example.courseskoinapp.repo.CourseRepository
import com.example.courseskoinapp.repo.CourseRepositoryImpl
import com.example.courseskoinapp.ui.auth.login.LoginViewModel
import com.example.courseskoinapp.ui.auth.onboard.OnBoardingViewModel
import com.example.courseskoinapp.ui.auth.signup.SignupViewModel
import com.example.courseskoinapp.ui.home.course.CourseViewModel
import com.example.courseskoinapp.ui.home.course.process.CourseDetailViewModel
import com.example.courseskoinapp.ui.home.profile.ProfileViewModel
import com.example.courseskoinapp.ui.home.watchlater.WatchLaterViewModel
import com.example.courseskoinapp.utils.Constants
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
val authModule = module {
    single { FirebaseAuth.getInstance() }
    single { Firebase.firestore }
    single { Firebase.storage }
    single<SharedPreferences> {
        val application: Application = get()
        application.getSharedPreferences(
            Constants.SHARED_PREFERENCES_NAME,
            Application.MODE_PRIVATE
        )
    }
    single { FirebaseAuthServices(get(), get(), get()) }
    single { FirebaseDatabaseService(get(), get()) }
    viewModel { OnBoardingViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { CourseDetailViewModel(get()) }
    viewModel { WatchLaterViewModel(get(), get()) }
}

val homeModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://www.usda.gov/sites/default/files/documents/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(CourseApiService::class.java)
    }
    single<CourseRepository> { CourseRepositoryImpl(get()) }
    viewModel { CourseViewModel(get(), get()) }
}

val appModule = listOf(authModule, homeModule)