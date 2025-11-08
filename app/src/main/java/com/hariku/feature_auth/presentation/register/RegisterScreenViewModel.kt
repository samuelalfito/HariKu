package com.hariku.feature_auth.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_auth.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Data class untuk menampung SEMUA state yang dibutuhkan oleh RegisterScreen.
 */
data class RegisterUiState(
    val name: String = "", // <-- TAMBAHAN
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val registerSuccess: Boolean = false, // <-- Diganti namanya
    val error: String? = null
)

/**
 * ViewModel untuk RegisterScreen.
 */
class RegisterScreenViewModel( // <-- Diganti namanya
    private val repository: AuthRepository
) : ViewModel() {

    // StateFlow privat untuk internal ViewModel
    private val _uiState = MutableStateFlow(RegisterUiState()) // <-- Pakai state baru
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
    fun onRegisterClicked() { // <-- Diganti namanya
        // Jangan proses jika sedang loading
        if (_uiState.value.isLoading) return

        // --- VALIDASI SISI KLIEN ---
        val currentState = _uiState.value
        if (currentState.name.isBlank() || currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(error = "Nama, email dan password tidak boleh kosong") }
            return
        }

        if (currentState.password != currentState.confirmPassword) {
            _uiState.update { it.copy(error = "Password tidak cocok") }
            return
        }

        // (Opsional) Anda bisa menambahkan validasi kekuatan password di sini
        if (currentState.password.length < 8) {
            _uiState.update { it.copy(error = "Password minimal 8 karakter") }
            return
        }

        // --- PROSES ---

        // Set state ke loading
        _uiState.update { it.copy(isLoading = true, error = null) }

        // Jalankan di coroutine
        viewModelScope.launch {
            // Panggil repository (menggunakan currentState yang sudah divalidasi)
            val result = repository.signUp(
                name = currentState.name,
                email = currentState.email,
                password = currentState.password
            )

            // Proses hasilnya
            result.onSuccess { authUser ->
                // Sukses
                Log.d("RegisterScreenViewModel", "Sign Up Success") // <-- Diganti namanya
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        registerSuccess = true // <-- Setel status sukses
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