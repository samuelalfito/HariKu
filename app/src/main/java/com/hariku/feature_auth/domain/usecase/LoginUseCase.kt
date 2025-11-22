package com.hariku.feature_auth.domain.usecase

import com.hariku.feature_auth.domain.model.AuthUser
import com.hariku.feature_auth.domain.repository.AuthRepository

/**
 * UseCase untuk Login.
 */
class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthUser> {
        // Validasi tambahan
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email dan password tidak boleh kosong"))
        }
        
        return repository.login(email, password)
    }
}

