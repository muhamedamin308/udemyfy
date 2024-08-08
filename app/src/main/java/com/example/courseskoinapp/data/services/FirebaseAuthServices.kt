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
        onAction: (User?, Exception?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener {
                it?.user?.let { firebaseUser ->
                    saveUserProfile(firebaseUser.uid, user, onAction)
                }
            }.addOnFailureListener {
                onAction(null, it)
            }
    }


    fun login(
        email: String,
        password: String,
        onAction: (FirebaseUser?, Exception?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it?.user?.let { user -> onAction(user, null) }
            }.addOnFailureListener { onAction(null, it) }
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    suspend fun generateRandomProfilePicture(): String? {
        val reference = storage.reference.child("profile_images")
        return try {
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

    fun getUserProfile(
        onAction: (User?, Exception?) -> Unit
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            store.collection(Constants.Collections.USER_COLLECTION).document(userId).get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)
                    if (user != null) {
                        onAction(user, null)
                    } else {
                        onAction(null, Exception("User profile not found"))
                    }
                }
                .addOnFailureListener { exception ->
                    onAction(null, exception)
                }
        } else {
            onAction(null, Exception("No authenticated user"))
        }
    }

    fun logout() = auth.signOut()

    private fun saveUserProfile(
        userId: String,
        user: User,
        onAction: (User?, Exception?) -> Unit
    ) {
        store.collection(Constants.Collections.USER_COLLECTION).document(userId).set(user)
            .addOnSuccessListener { onAction(user, null) }
            .addOnFailureListener { onAction(null, it) }
    }

}