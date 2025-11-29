package com.hariku.feature_auth.domain.usecase

import com.hariku.feature_auth.domain.model.AuthUser
import com.hariku.feature_auth.domain.repository.AuthRepository

class LoginWithGoogleUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(id: String): Result<AuthUser> {
        return repository.loginWithGoogle(id)
    }
}