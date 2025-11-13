package com.hariku.feature_auth.domain.usecase

import com.hariku.feature_auth.domain.repository.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() {
        repository.logout()
    }
}