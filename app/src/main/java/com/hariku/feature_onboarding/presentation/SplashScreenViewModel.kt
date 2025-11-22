package com.hariku.feature_onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_auth.domain.usecase.CheckLoginStatusUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

enum class AuthNavigationState {
    LOADING,        // Sedang memeriksa
    AUTHENTICATED,  // Sudah login, arahkan ke Home
    UNAUTHENTICATED // Belum login, arahkan ke Login
}

class SplashScreenViewModel(
    private val checkLoginStatusUseCase: CheckLoginStatusUseCase
) : ViewModel() {

    private val _navState = MutableStateFlow(AuthNavigationState.LOADING)
    val navState: StateFlow<AuthNavigationState> = _navState.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            // Kita ingin melakukan DUA hal:
            // 1. Tampilkan splash minimal 2.5 detik
            // 2. Cek status login
            // Navigasi hanya terjadi setelah KEDUANYA selesai.

            coroutineScope {
                // Jalankan timer minimal
                val timerJob = async { delay(2500) }

                // Ambil hasil auth (hanya hasil pertama, bukan Flow-nya)
                val authJob = async { checkLoginStatusUseCase().first() }

                // Tunggu kedua 'job' selesai
                timerJob.await()
                val authUser = authJob.await()

                // Sekarang putuskan navigasi
                _navState.value = if (authUser != null) {
                    AuthNavigationState.AUTHENTICATED
                } else {
                    AuthNavigationState.UNAUTHENTICATED
                }
            }
        }
    }
}