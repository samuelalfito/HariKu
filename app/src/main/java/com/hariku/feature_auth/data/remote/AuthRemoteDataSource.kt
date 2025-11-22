package com.hariku.feature_auth.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_auth.data.dto.UserDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Remote Data Source untuk Authentication.
 * Menangani semua interaksi dengan Firebase Auth dan Firestore.
 */
class AuthRemoteDataSource(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    
    private val usersCollection = firestore.collection("Users")

    suspend fun login(email: String, password: String): FirebaseUser {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        return authResult.user ?: throw IllegalStateException("User null setelah login")
    }

    suspend fun loginWithGoogle(id: String): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(id, null)
        val authResult = auth.signInWithCredential(credential).await()
        return authResult.user ?: throw IllegalStateException("User null setelah login")
    }

    suspend fun signUp(email: String, password: String, name: String): FirebaseUser {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val firebaseUser = authResult.user ?: throw IllegalStateException("User null setelah sign up")

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            // .setPhotoUri(Uri.parse("url_foto_jika_ada"))
            .build()

        firebaseUser.updateProfile(profileUpdates).await()

        return firebaseUser
    }

    /**
     * Mendapatkan current user dari Firebase Auth
     */
    fun getCurrentFirebaseUser(): FirebaseUser? {
        return auth.currentUser
    }

    /**
     * Mendapatkan data user dari Firestore berdasarkan UID
     */
    suspend fun getUserFromFirestore(uid: String): UserDto? {
        return try {
            val document = usersCollection.document(uid).get().await()
            document.toObject(UserDto::class.java)
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Menyimpan data user ke Firestore
     */
    suspend fun saveUserToFirestore(uid: String, userData: Map<String, Any?>) {
        usersCollection.document(uid).set(userData).await()
    }

    fun logout() {
        auth.signOut()
    }

    fun getAuthStateFlow(): Flow<FirebaseUser?> {
        return callbackFlow {
            val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                // bisa null jika logout
                trySend(firebaseAuth.currentUser)
            }

            auth.addAuthStateListener(authStateListener)

            awaitClose {
                auth.removeAuthStateListener(authStateListener)
            }
        }
    }
}

