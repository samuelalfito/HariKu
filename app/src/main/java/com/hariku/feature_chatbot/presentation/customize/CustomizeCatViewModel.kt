package com.hariku.feature_chatbot.presentation.customize

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hariku.feature_chatbot.domain.model.Chatbot
import com.hariku.feature_chatbot.domain.usecase.AddChatbotUseCase
import kotlinx.coroutines.launch
import java.util.UUID

class CustomizeCatViewModel(
    private val addChatbotUseCase: AddChatbotUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    
    var uiState by mutableStateOf(CustomizeCatUiState())
        private set
    
    fun addChatbot(
        name: String,
        subtitle: String,
        description: String,
        avatarResId: Int
    ) {
        val userId = firebaseAuth.currentUser?.uid ?: return
        
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            
            val chatbot = Chatbot(
                id = UUID.randomUUID().toString(),
                name = name,
                subtitle = subtitle,
                description = description,
                avatarResId = avatarResId,
                createdAt = System.currentTimeMillis(),
                userId = userId
            )
            
            val result = addChatbotUseCase(chatbot)
            
            result.fold(
                onSuccess = {
                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = true,
                        error = null
                    )
                },
                onFailure = { exception ->
                    uiState = uiState.copy(
                        isLoading = false,
                        isSuccess = false,
                        error = exception.message ?: "Gagal menambahkan chatbot"
                    )
                }
            )
        }
    }
    
    fun resetState() {
        uiState = CustomizeCatUiState()
    }
}

data class CustomizeCatUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)