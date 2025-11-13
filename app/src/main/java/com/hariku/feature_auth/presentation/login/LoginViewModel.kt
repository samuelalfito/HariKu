package com.hariku.feature_auth.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Data class untuk menampung SEMUA state yang dibutuhkan oleh UI.
 * Ini adalah "Single Source of Truth" untuk LoginScreen.
 */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel untuk LoginScreen.
 * Bergantung pada UseCase, bukan langsung ke Repository.
 * Clean Architecture: UI → ViewModel → UseCase → Repository
 */
class LoginScreenViewModel(
    private val useCase: LoginUseCase
) : ViewModel() {

    // StateFlow privat untuk internal ViewModel
    private val _uiState = MutableStateFlow(LoginUiState())
    // StateFlow publik yang hanya bisa dibaca (read-only) oleh UI
    val uiState = _uiState.asStateFlow()

    /**
     * Dipanggil oleh UI (TextField) setiap kali email berubah.
     */
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    /**
     * Dipanggil oleh UI (TextField) setiap kali password berubah.
     */
    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    /**
     * Dipanggil oleh UI (Button) saat tombol login diklik.
     */
    fun onLoginClicked() {
        // Jangan proses jika sedang loading
        if (_uiState.value.isLoading) return

        // Set state ke loading
        _uiState.update { it.copy(isLoading = true, error = null) }

        // Jalankan di coroutine
        viewModelScope.launch {
            val currentState = _uiState.value

            // Panggil UseCase
            val result = useCase(currentState.email, currentState.password)

            // Proses hasilnya
            result.onSuccess { authUser ->
                // Sukses
                Log.d("LoginScreenViewModel", "Login Success: ${authUser.name}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSuccess = true
                    )
                }
            }.onFailure { exception ->
                // Gagal
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = exception.message ?: "Terjadi kesalahan"
                    )
                }
            }
        }
    }

    /**
     * Dipanggil oleh UI setelah pesan error ditampilkan (misalnya di Snackbar).
     * Ini untuk mencegah error ditampilkan berulang kali.
     */
    fun onErrorShown() {
        _uiState.update { it.copy(error = null) }
    }
}

