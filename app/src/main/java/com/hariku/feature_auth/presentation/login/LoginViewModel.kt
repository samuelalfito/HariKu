package com.hariku.feature_auth.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_auth.domain.usecase.LoginUseCase
import com.hariku.feature_auth.domain.usecase.LoginWithGoogleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel untuk LoginScreen.
 */
class LoginScreenViewModel(
    private val loginUseCase: LoginUseCase,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

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
     * Saat tombol login diklik.
     */
    fun onLoginClicked() {
        // Loading lock
        if (_uiState.value.isLoading) return

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val currentState = _uiState.value

            val result = loginUseCase(currentState.email, currentState.password)

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

    fun onGoogleSignInSuccess(idToken: String) {
        // Loading lock
        if (_uiState.value.isLoading) return

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = loginWithGoogleUseCase(idToken) // Panggil use case baru

            result.onSuccess { authUser ->
                Log.d("LoginVM", "Google Login Success: ${authUser.name}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSuccess = true
                    )
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = exception.message ?: "Terjadi kesalahan"
                    )
                }
            }
        }
    }

    fun onGoogleSignInFailed(errorMessage: String) {
        _uiState.update {
            it.copy(loginSuccess = false)
        }
        Log.e("LoginVM", "Google Login Failed: $errorMessage")
    }

    /**
     * Dipanggil oleh UI setelah pesan error ditampilkan.
     * Mencegah error ditampilkan berulang kali.
     */
    fun onErrorShown() {
        _uiState.update { it.copy(error = null) }
    }
}

