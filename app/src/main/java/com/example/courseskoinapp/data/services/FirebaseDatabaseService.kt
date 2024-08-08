package com.example.courseskoinapp.data.services

import com.example.courseskoinapp.data.model.Release
import com.example.courseskoinapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.component.KoinComponent

/**
 * @author Muhamed Amin Hassan on 03,August,2024
 * @see <a href="https://github.com/muhamedamin308">Muhamed's Github</a>,
 * Egypt, Cairo.
 */
class FirebaseDatabaseService(
    private val auth: FirebaseAuth,
    store: FirebaseFirestore
) : KoinComponent {

    private val watchLaterCollectionPath = store.collection(Constants.Collections.USER_COLLECTION)
        .document(auth.uid!!)
        .collection(Constants.Collections.WATCH_LATER_COLLECTION)

    fun getWatchLaterCourses(
        onAction: (List<Release>?, List<DocumentSnapshot>?, Exception?) -> Unit
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            watchLaterCollectionPath.addSnapshotListener { value, error ->
                if (error != null || value == null) {
                    onAction(null, null, error)
                } else {
                    val courses = value.toObjects(Release::class.java)
                    val documents = value.documents
                    onAction(courses, documents, null)
                }
            }
        } else {
            onAction(null, null, Exception("No authenticated user"))
        }
    }


    fun addToWatchLater(
        course: Release,
        onAction: (Release?, Exception?) -> Unit
    ) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            watchLaterCollectionPath.document().set(course)
                .addOnSuccessListener { onAction(course, null) }
                .addOnFailureListener { onAction(null, it) }
        } else {
            onAction(null, Exception("No authenticated user"))
        }
    }

    fun deleteFromWatchLater(
        courseIndex: Int?,
        watchLaterDocument: List<DocumentSnapshot>
    ) {
        if (courseIndex != null && courseIndex != -1) {
            val documentId = watchLaterDocument[courseIndex].id
            watchLaterCollectionPath.document(documentId).delete()
        }
    }
}