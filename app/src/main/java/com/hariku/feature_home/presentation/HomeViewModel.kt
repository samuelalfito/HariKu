package com.hariku.feature_home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_chatbot.domain.model.ChatbotWithHistory
import com.hariku.feature_chatbot.domain.usecase.GetChatbotsWithHistoryUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(
    private val getChatbotsWithHistoryUseCase: GetChatbotsWithHistoryUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    var chatbotUiState by mutableStateOf(ChatbotUiState())
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
                        chatbotUiState = chatbotUiState.copy(
                            chatbots = chatbots,
                            isLoading = false,
                            error = null
                        )
                    },
                    onFailure = { exception ->
                        chatbotUiState = chatbotUiState.copy(
                            isLoading = false,
                            error = exception.message ?: "Gagal memuat chatbot"
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        chatbotUiState = chatbotUiState.copy(isLoading = true)
        loadChatbots()
    }
}

data class ChatbotUiState(
    val chatbots: List<ChatbotWithHistory> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

