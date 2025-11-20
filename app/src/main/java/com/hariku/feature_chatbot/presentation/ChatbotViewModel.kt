package com.hariku.feature_chatbot.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hariku.feature_chatbot.domain.model.Chatbot
import com.hariku.feature_chatbot.domain.usecase.GetChatbotsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatbotViewModel(
    private val getChatbotsUseCase: GetChatbotsUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    
    var uiState by mutableStateOf(ChatbotUiState())
        private set
    
    init {
        loadChatbots()
    }
    
    private fun loadChatbots() {
        val userId = firebaseAuth.currentUser?.uid ?: return
        
        getChatbotsUseCase(userId)
            .onEach { result ->
                result.fold(
                    onSuccess = { chatbots ->
                        uiState = uiState.copy(
                            chatbots = chatbots,
                            isLoading = false,
                            error = null
                        )
                    },
                    onFailure = { exception ->
                        uiState = uiState.copy(
                            isLoading = false,
                            error = exception.message ?: "Gagal memuat chatbot"
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }
    
    fun refresh() {
        uiState = uiState.copy(isLoading = true)
        loadChatbots()
    }
}

data class ChatbotUiState(
    val chatbots: List<Chatbot> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

