package com.hariku.feature_chatbot.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hariku.feature_chatbot.domain.model.Chatbot
import com.hariku.feature_chatbot.domain.model.ChatMessage
import com.hariku.feature_chatbot.domain.usecase.GetChatMessagesUseCase
import com.hariku.feature_chatbot.domain.usecase.GetChatbotByIdUseCase
import com.hariku.feature_chatbot.domain.usecase.MarkMessagesAsReadUseCase
import com.hariku.feature_chatbot.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID

class ChatbotDetailViewModel(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val getChatbotByIdUseCase: GetChatbotByIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val markMessagesAsReadUseCase: MarkMessagesAsReadUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    
    var uiState by mutableStateOf(ChatbotDetailUiState())
        private set
    
    private var currentChatbotId: String = ""
    
    fun initialize(chatbotId: String) {
        currentChatbotId = chatbotId
        loadChatbot()
        loadMessages()
        markAsRead()
    }
    
    private fun loadChatbot() {
        val userId = firebaseAuth.currentUser?.uid ?: return
        
        viewModelScope.launch {
            val result = getChatbotByIdUseCase(currentChatbotId, userId)
            result.fold(
                onSuccess = { chatbot ->
                    uiState = uiState.copy(chatbot = chatbot)
                },
                onFailure = { exception ->
                    uiState = uiState.copy(
                        error = exception.message ?: "Gagal memuat chatbot"
                    )
                }
            )
        }
    }
    
    private fun loadMessages() {
        val userId = firebaseAuth.currentUser?.uid ?: return
        
        getChatMessagesUseCase(currentChatbotId, userId)
            .onEach { result ->
                result.fold(
                    onSuccess = { messages ->
                        uiState = uiState.copy(
                            messages = messages,
                            isLoading = false,
                            error = null
                        )
                    },
                    onFailure = { exception ->
                        uiState = uiState.copy(
                            isLoading = false,
                            error = exception.message ?: "Gagal memuat pesan"
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }
    
    private fun markAsRead() {
        val userId = firebaseAuth.currentUser?.uid ?: return
        
        viewModelScope.launch {
            markMessagesAsReadUseCase(currentChatbotId, userId)
        }
    }
    
    fun sendMessage(messageText: String) {
        if (messageText.isBlank() || uiState.isSending) return
        
        val userId = firebaseAuth.currentUser?.uid ?: return
        
        val message = ChatMessage(
            id = UUID.randomUUID().toString(),
            chatbotId = currentChatbotId,
            userId = userId,
            message = messageText.trim(),
            isFromUser = true,
            timestamp = System.currentTimeMillis(),
            isRead = true
        )
        
        viewModelScope.launch {
            uiState = uiState.copy(isSending = true, error = null)
            
            val result = sendMessageUseCase(message)
            
            result.fold(
                onSuccess = {
                    uiState = uiState.copy(isSending = false)
                },
                onFailure = { exception ->
                    uiState = uiState.copy(
                        isSending = false,
                        error = exception.message ?: "Gagal mengirim pesan"
                    )
                }
            )
        }
    }
    
    fun clearError() {
        uiState = uiState.copy(error = null)
    }
}

data class ChatbotDetailUiState(
    val chatbot: Chatbot? = null,
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = true,
    val isSending: Boolean = false,
    val error: String? = null
)

