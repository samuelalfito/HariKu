package com.hariku.feature_auth.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_auth.domain.usecase.LoginWithGoogleUseCase
import com.hariku.feature_auth.domain.usecase.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
 */
class RegisterScreenViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * Setiap nama berubah.
     */
    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, error = null) }
    }

    /**
     * Setiap email berubah.
     */
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    /**
     * Setiap password berubah.
     */
    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    /**
     * Setiap konfirmasi password berubah.
     */
    fun onConfirmPasswordChange(password: String) {
        _uiState.update { it.copy(confirmPassword = password, error = null) }
    }

    /**
     * Saat tombol register diklik.
     */
    fun onRegisterClicked() {
        // Loading lock
        if (_uiState.value.isLoading) return

        val currentState = _uiState.value
        
        if (currentState.password != currentState.confirmPassword) {
            _uiState.update { it.copy(error = "Password tidak cocok") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = signUpUseCase(
                name = currentState.name,
                email = currentState.email,
                password = currentState.password
            )

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