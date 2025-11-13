package com.hariku.feature_auth.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_auth.data.dto.UserDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Remote Data Source untuk Authentication.
 * Menangani semua interaksi dengan Firebase Auth dan Firestore.
 * Ini memisahkan logic Firebase dari Repository.
 */
class AuthRemoteDataSource(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    
    private val usersCollection = firestore.collection("Users")
    
    /**
     * Login dengan email dan password menggunakan Firebase Auth
     */
    suspend fun login(email: String, password: String): FirebaseUser {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        return authResult.user ?: throw IllegalStateException("User null setelah login")
    }
    
    /**
     * Sign up (registrasi) dengan email dan password menggunakan Firebase Auth
     */
    suspend fun signUp(email: String, password: String): FirebaseUser {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        return authResult.user ?: throw IllegalStateException("User null setelah sign up")
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
    
    /**
     * Logout dari Firebase Auth
     */
    fun logout() {
        auth.signOut()
    }

    fun getAuthStateFlow(): Flow<FirebaseUser?> {
        return callbackFlow {
            val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                // Mengirimkan FirebaseUser saat ini (bisa null jika logout)
                trySend(firebaseAuth.currentUser)
            }

            // Daftarkan listener
            auth.addAuthStateListener(authStateListener)

            // Hapus listener saat Flow ditutup
            awaitClose {
                auth.removeAuthStateListener(authStateListener)
            }
        }
    }
}

