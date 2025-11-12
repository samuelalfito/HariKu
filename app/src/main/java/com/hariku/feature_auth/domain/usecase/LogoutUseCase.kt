package com.hariku.feature_auth.domain.usecase

import com.hariku.feature_auth.domain.repository.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository
) {
    // Kita gunakan 'operator fun invoke' agar bisa dipanggil sbg fungsi
    operator fun invoke() {
        repository.logout()
    }
}