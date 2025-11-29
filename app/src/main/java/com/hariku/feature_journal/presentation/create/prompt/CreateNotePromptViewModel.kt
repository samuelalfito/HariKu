package com.hariku.feature_journal.presentation.create.prompt

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_journal.domain.model.PromptRequest
import com.hariku.feature_journal.domain.model.PromptResponse
import com.hariku.feature_journal.domain.usecase.GeneratePromptsUseCase
import kotlinx.coroutines.launch

class CreateNotePromptViewModel(
    private val generatePromptsUseCase: GeneratePromptsUseCase,
    private val sharedViewModel: SharedPromptViewModel
) : ViewModel() {
    
    private val _uiState = mutableStateOf(CreateNotePromptUiState())
    val uiState: State<CreateNotePromptUiState> = _uiState
    
    val availableFeelings = listOf(
        "Senang", "Sedih", "Cemas", "Marah",
        "Takut", "Stress", "Lelah", "Kecewa",
        "Termotivasi", "Ingin Fokus", "Bosan",
        "Kesepian", "Damai", "Malu", "Muak"
    )
    
    fun toggleFeeling(feeling: String) {
        val current = _uiState.value.selectedFeelings.toMutableSet()
        if (current.contains(feeling)) {
            current.remove(feeling)
        } else {
            current.add(feeling)
        }
        _uiState.value = _uiState.value.copy(selectedFeelings = current)
    }
    
    fun setCustomFeeling(text: String) {
        _uiState.value = _uiState.value.copy(customFeeling = text)
    }
    
    fun generatePrompts(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val request = PromptRequest(
                selectedFeelings = _uiState.value.selectedFeelings.toList(),
                customFeeling = _uiState.value.customFeeling
            )
            
            val result = generatePromptsUseCase(request)
            
            result.onSuccess { response ->
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    generatedResponse = response
                )
                
                sharedViewModel.setPromptResponse(response)
                
                onSuccess()
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Gagal generate prompts"
                )
            }
        }
    }
}

data class CreateNotePromptUiState(
    val selectedFeelings: Set<String> = emptySet(),
    val customFeeling: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val generatedResponse: PromptResponse? = null
)