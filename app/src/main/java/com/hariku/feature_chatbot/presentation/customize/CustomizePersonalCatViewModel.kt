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

class CustomizePersonalCatViewModel(
    private val addChatbotUseCase: AddChatbotUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    
    var uiState by mutableStateOf(CustomizePersonalCatUiState())
        private set
    
    fun updateLanguageStyle(value: Float) {
        uiState = uiState.copy(languageStyle = value)
    }
    
    fun updateProfessionalStyle(value: Float) {
        uiState = uiState.copy(professionalStyle = value)
    }
    
    fun updateFeedbackType(type: String) {
        uiState = uiState.copy(feedbackType = type)
    }
    
    fun updateGoals(goals: Set<String>) {
        uiState = uiState.copy(selectedGoals = goals)
    }
    
    fun updateOtherGoal(goal: String) {
        uiState = uiState.copy(otherGoal = goal)
    }
    
    fun saveChatbot(
        name: String,
        avatarResId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            onError("User tidak terautentikasi")
            return
        }
        
        uiState = uiState.copy(isSaving = true)
        
        viewModelScope.launch {
            try {
                val subtitle = buildSubtitle()
                val description = buildDescription()
                
                val chatbot = Chatbot(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    subtitle = subtitle,
                    description = description,
                    avatarResId = avatarResId,
                    createdAt = System.currentTimeMillis(),
                    userId = userId
                )
                
                addChatbotUseCase(chatbot)
                    .onSuccess {
                        uiState = uiState.copy(isSaving = false)
                        onSuccess()
                    }
                    .onFailure { error ->
                        uiState = uiState.copy(isSaving = false)
                        onError(error.message ?: "Gagal menyimpan chatbot")
                    }
            } catch (e: Exception) {
                uiState = uiState.copy(isSaving = false)
                onError(e.message ?: "Terjadi kesalahan")
            }
        }
    }
    
    private fun buildSubtitle(): String {
        val languageText = when {
            uiState.languageStyle <= 0.33f -> "Formal"
            uiState.languageStyle >= 0.67f -> "Santai"
            else -> "Netral"
        }
        
        val professionalText = when {
            uiState.professionalStyle <= 0.33f -> "Profesional"
            uiState.professionalStyle >= 0.67f -> "Ceria"
            else -> "Seimbang"
        }
        
        return "$languageText dan $professionalText"
    }
    
    private fun buildDescription(): String {
        val parts = mutableListOf<String>()
        
        parts.add("Tipe Feedback: ${uiState.feedbackType}")
        
        if (uiState.selectedGoals.isNotEmpty()) {
            val goalsText = uiState.selectedGoals.joinToString(", ")
            parts.add("Tujuan: $goalsText")
        }
        
        if (uiState.otherGoal.isNotBlank()) {
            parts.add("Tujuan Lainnya: ${uiState.otherGoal}")
        }
        
        return parts.joinToString(". ")
    }
}

data class CustomizePersonalCatUiState(
    val languageStyle: Float = 0.5f,
    val professionalStyle: Float = 0.5f,
    val feedbackType: String = "CBT-Based",
    val selectedGoals: Set<String> = setOf("Refleksi Diri"),
    val otherGoal: String = "",
    val isSaving: Boolean = false
)

