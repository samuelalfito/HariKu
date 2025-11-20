package com.hariku.feature_chatbot.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hariku.feature_chatbot.domain.model.ChatbotWithHistory
import com.hariku.feature_chatbot.domain.usecase.GetChatbotsWithHistoryUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatbotViewModel(
    private val getChatbotsWithHistoryUseCase: GetChatbotsWithHistoryUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    
    var uiState by mutableStateOf(ChatbotUiState())
        private set
    
    init {
        loadChatbots()
    }
    
    private fun loadChatbots() {
        val userId = firebaseAuth.currentUser?.uid ?: return
        
        getChatbotsWithHistoryUseCase(userId)
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
    val chatbots: List<ChatbotWithHistory> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
