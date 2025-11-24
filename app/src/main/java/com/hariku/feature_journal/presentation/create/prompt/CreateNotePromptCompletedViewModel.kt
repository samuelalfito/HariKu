package com.hariku.feature_journal.presentation.create.prompt

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_journal.domain.model.PromptResponse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CreateNotePromptCompletedViewModel(
    private val sharedViewModel: SharedPromptViewModel
) : ViewModel() {
    
    companion object {
        private const val TAG = "PromptCompletedVM"
    }
    
    private val _uiState = mutableStateOf(CreateNotePromptCompletedUiState())
    val uiState: State<CreateNotePromptCompletedUiState> = _uiState
    
    init {
        val currentResponse = sharedViewModel.latestPromptResponse.value
        if (currentResponse != null) {
            _uiState.value = _uiState.value.copy(
                promptResponse = currentResponse,
                isLoading = false
            )
        } else {
            observeSharedPrompts()
        }
    }
    
    private fun observeSharedPrompts() {
        sharedViewModel.latestPromptResponse
            .onEach { response ->
                Log.d(TAG, "SharedViewModel emitted update: ${response != null}")
                
                if (response != null) {
                    response.suggestions.forEachIndexed { index, suggestion ->
                        Log.d(TAG, "  Suggestion $index: ${suggestion.text.take(50)}...")
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        promptResponse = response,
                        isLoading = false
                    )
                } else {
                    Log.w(TAG, "Received null response from SharedViewModel!")
                }
            }
            .launchIn(viewModelScope)
    }
    
    fun selectPrompt(promptId: String) {
        val response = _uiState.value.promptResponse ?: return
        val updatedSuggestions = response.suggestions.map { suggestion ->
            if (suggestion.id == promptId) {
                suggestion.copy(isSelected = !suggestion.isSelected)
            } else {
                suggestion.copy(isSelected = false)
            }
        }
        
        _uiState.value = _uiState.value.copy(
            promptResponse = response.copy(suggestions = updatedSuggestions)
        )
    }
}

data class CreateNotePromptCompletedUiState(
    val promptResponse: PromptResponse? = null,
    val isLoading: Boolean = true
)