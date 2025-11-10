package com.hariku.feature_auth.domain.repository

import com.hariku.feature_auth.domain.model.AuthUser

/**
 * Interface Repository untuk Authentication (Domain Layer).
 * ViewModel/UseCase akan bergantung pada interface ini, bukan implementasinya.
 */
interface AuthRepository {
    /**
     * Login dengan email dan password.
     */
    suspend fun login(email: String, password: String): Result<AuthUser>

    /**
     * Sign up (registrasi) dengan email, password, dan nama.
     */
    suspend fun signUp(email: String, password: String, name: String): Result<AuthUser>

    /**
     * Mendapatkan user yang sedang login saat ini.
     */
    fun getCurrentUser(): AuthUser?
}

