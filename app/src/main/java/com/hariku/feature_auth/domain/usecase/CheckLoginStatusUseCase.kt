package com.hariku.feature_auth.domain.usecase

import com.hariku.feature_auth.domain.repository.AuthRepository

class CheckLoginStatusUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getAuthState()
}