package com.hariku.feature_auth.domain.usecase

import com.hariku.feature_auth.domain.model.AuthUser
import com.hariku.feature_auth.domain.repository.AuthRepository

/**
 * UseCase untuk Sign Up (Registrasi).
 * Memisahkan logika bisnis dari ViewModel.
 */
class SignUpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): Result<AuthUser> {
        // Validasi tambahan bisa ditambahkan di sini
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Semua field harus diisi"))
        }
        
        if (password.length < 8) {
            return Result.failure(IllegalArgumentException("Password minimal 8 karakter"))
        }
        
        return repository.signUp(email, password, name)
    }
}

