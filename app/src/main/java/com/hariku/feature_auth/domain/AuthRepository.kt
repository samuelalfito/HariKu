package com.hariku.feature_auth.domain

import com.google.firebase.auth.FirebaseUser

/**
 * Data class sederhana untuk "membungkus" data user dari Firebase.
 * Ini adalah praktik yang baik agar ViewModel tidak perlu tahu soal "FirebaseUser".
 */
data class AuthUser(
    val uid: String,
    val email: String?,
    val name: String? // <-- TAMBAHAN
)

/**
 * Ini adalah Interface (kontrak) untuk Repository.
 * ViewModel akan bergantung pada ini, bukan pada implementasi konkritnya.
 * Ini membuat ViewModel Anda SANGAT MUDAH di-test.
 */
interface AuthRepository {

    /**
     * Melakukan login dengan email dan password.
     * Mengembalikan Result yang berisi AuthUser jika sukses, atau Exception jika gagal.
     */
    suspend fun login(email: String, password: String): Result<AuthUser>

    /**
     * Melakukan sign up (daftar) dengan email dan password.
     * Mengembalikan Result yang berisi AuthUser jika sukses, atau Exception jika gagal.
     */
    suspend fun signUp(email: String, password: String, name: String): Result<AuthUser>

    /**
     * (Opsional) Mendapatkan user yang sedang login saat ini.
     */
    fun getCurrentUser(): AuthUser?
}