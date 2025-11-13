package com.hariku.feature_auth.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_auth.domain.usecase.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Data class untuk menampung SEMUA state yang dibutuhkan oleh RegisterScreen.
 */
data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val registerSuccess: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel untuk RegisterScreen.
 * Bergantung pada UseCase, bukan langsung ke Repository.
 * Clean Architecture: UI → ViewModel → UseCase → Repository
 */
class RegisterScreenViewModel(
    private val useCase: SignUpUseCase
) : ViewModel() {

    // StateFlow privat untuk internal ViewModel
    private val _uiState = MutableStateFlow(RegisterUiState())
    // StateFlow publik yang hanya bisa dibaca (read-only) oleh UI
    val uiState = _uiState.asStateFlow()

    /**
     * Dipanggil oleh UI (TextField) setiap kali nama berubah.
     */
    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, error = null) }
    }

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
     * Dipanggil oleh UI (TextField) setiap kali konfirmasi password berubah.
     */
    fun onConfirmPasswordChange(password: String) {
        _uiState.update { it.copy(confirmPassword = password, error = null) }
    }

    /**
     * Dipanggil oleh UI (Button) saat tombol register diklik.
     */
    fun onRegisterClicked() {
        // Jangan proses jika sedang loading
        if (_uiState.value.isLoading) return

        val currentState = _uiState.value
        
        // Validasi password match (ini tetap di ViewModel karena UI specific)
        if (currentState.password != currentState.confirmPassword) {
            _uiState.update { it.copy(error = "Password tidak cocok") }
            return
        }

        // Set state ke loading
        _uiState.update { it.copy(isLoading = true, error = null) }

        // Jalankan di coroutine
        viewModelScope.launch {
            // Panggil UseCase (validasi lain sudah di dalam UseCase)
            val result = useCase(
                name = currentState.name,
                email = currentState.email,
                password = currentState.password
            )

            // Proses hasilnya
            result.onSuccess { authUser ->
                // Sukses
                Log.d("RegisterScreenViewModel", "Sign Up Success: ${authUser.name}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        registerSuccess = true
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
     */
    fun onErrorShown() {
        _uiState.update { it.copy(error = null) }
    }
}