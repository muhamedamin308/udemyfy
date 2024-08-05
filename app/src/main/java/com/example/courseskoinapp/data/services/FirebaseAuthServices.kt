package com.example.courseskoinapp.data.services

import com.example.courseskoinapp.data.model.User
import com.example.courseskoinapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import kotlin.random.Random

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class FirebaseAuthServices(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val storage: FirebaseStorage
) : KoinComponent {

    fun createAccount(
        user: User,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener {
                it?.user?.let { firebaseUser ->
                    saveUserProfile(firebaseUser.uid, user, onSuccess, onFailure)
                }
            }.addOnFailureListener {
                onFailure(it)
            }
    }


    fun login(
        email: String,
        password: String,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it?.user?.let { user -> onSuccess(user) }
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    fun getUserName(onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        getUserProfile({ user ->
            onSuccess(user.name)
        }, { exception ->
            onFailure(exception)
        })
    }

    suspend fun generateRandomProfilePicture(): String? {
        val reference = storage.reference.child("profile_images")
        return try{
            val result = reference.listAll().await()
            val images = result.items
            if (images.isNotEmpty()) {
                val randomImage = images[Random.nextInt(images.size)]
                randomImage.downloadUrl.await().toString()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun getUserProfile(
        onSuccess: (User) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            store.collection(Constants.Collections.USER_COLLECTION).document(userId).get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)
                    if (user != null) {
                        onSuccess(user)
                    } else {
                        onFailure(Exception("User profile not found"))
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        } else {
            onFailure(Exception("No authenticated user"))
        }
    }


    private fun saveUserProfile(
        userId: String,
        user: User,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        store.collection(Constants.Collections.USER_COLLECTION).document(userId).set(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

}