package com.hariku.feature_chatbot.presentation.customize

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hariku.feature_chatbot.domain.model.ChatbotDraft

class CustomizeNewCatViewModel : ViewModel() {
    
    var uiState by mutableStateOf(CustomizeNewCatUiState())
        private set
    
    fun updateName(name: String) {
        uiState = uiState.copy(
            name = name,
            isValid = name.trim().isNotBlank() && uiState.selectedAvatarIndex >= 0
        )
    }
    
    fun selectAvatar(index: Int) {
        uiState = uiState.copy(
            selectedAvatarIndex = index,
            isValid = uiState.name.trim().isNotBlank() && index >= 0
        )
    }
    
    fun createDraft(avatarList: List<Int>): ChatbotDraft {
        val avatarResId = if (uiState.selectedAvatarIndex >= 0 && uiState.selectedAvatarIndex < avatarList.size) {
            avatarList[uiState.selectedAvatarIndex]
        } else {
            0
        }
        
        return ChatbotDraft.create(
            name = uiState.name,
            avatarResId = avatarResId
        )
    }
}

data class CustomizeNewCatUiState(
    val name: String = "",
    val selectedAvatarIndex: Int = 0,
    val isValid: Boolean = false
)

