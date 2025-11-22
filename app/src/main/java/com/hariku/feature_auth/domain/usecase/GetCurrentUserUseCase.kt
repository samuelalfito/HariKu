package com.hariku.feature_auth.domain.usecase

import com.hariku.feature_auth.domain.model.AuthUser
import com.hariku.feature_auth.domain.repository.AuthRepository

/**
 * UseCase untuk mendapatkan user yang sedang login.
 */
class GetCurrentUserUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(): AuthUser? {
        return repository.getCurrentUser()
    }
}

