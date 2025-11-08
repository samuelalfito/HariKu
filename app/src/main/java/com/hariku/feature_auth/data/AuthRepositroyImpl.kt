package com.hariku.feature_auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_auth.domain.AuthRepository
import com.hariku.feature_auth.domain.AuthUser
import kotlinx.coroutines.tasks.await

/**
 * Ini adalah implementasi konkrit dari AuthRepository.
 * Dia "tahu" cara berbicara dengan Firebase Auth dan Firestore.
 */
class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore // <-- DEPENDENSI BARU
) : AuthRepository {

    private val usersCollection = firestore.collection("Users")

    override suspend fun login(email: String, password: String): Result<AuthUser> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
                ?: throw IllegalStateException("FirebaseUser null setelah login sukses")

            // Setelah login, ambil data nama dari Firestore
            val userDocument = usersCollection.document(firebaseUser.uid).get().await()
            val name = userDocument.getString("name")

            // Konversi ke AuthUser
            Result.success(firebaseUser.toAuthUser(name))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String, name: String): Result<AuthUser> {
        return try {
            // 1. Buat user di Firebase Auth
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
                ?: throw IllegalStateException("FirebaseUser null setelah signup sukses")

            // 2. Buat data pengguna untuk Firestore
            val userMap = hashMapOf(
                "uid" to firebaseUser.uid,
                "name" to name,
                "email" to email
            )

            // 3. Simpan data pengguna ke collection "Users" di Firestore
            usersCollection.document(firebaseUser.uid).set(userMap).await()

            // 4. Kirim email verifikasi (opsional, tapi bagus)
            // firebaseUser.sendEmailVerification().await()

            Result.success(firebaseUser.toAuthUser(name))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): AuthUser? {
        val firebaseUser = auth.currentUser ?: return null

        // Saat getCurrentUser dipanggil, kita tidak bisa (secara sinkron)
        // mengambil nama dari firestore. Kita bisa biarkan null di sini,
        // dan biarkan Splash/Home ViewModel yang mengambil data lengkapnya.
        // Untuk saat ini, kita kembalikan apa yang kita tahu.
        return firebaseUser.toAuthUser(null)
        // TODO: Pertimbangkan untuk membuat getCurrentUser() sebagai suspend
        // dan mengambil data lengkap dari firestore.
    }

    /**
     * Fungsi helper untuk mengubah FirebaseUser (dari Firebase)
     * menjadi AuthUser (milik domain kita).
     */
    private fun FirebaseUser.toAuthUser(name: String?): AuthUser {
        return AuthUser(
            uid = this.uid,
            email = this.email,
            name = name // <-- Nama diteruskan
        )
    }
}