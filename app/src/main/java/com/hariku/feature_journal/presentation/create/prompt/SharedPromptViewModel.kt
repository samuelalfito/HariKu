package com.hariku.feature_journal.presentation.create.prompt

import androidx.lifecycle.ViewModel
import com.hariku.feature_journal.domain.model.PromptResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedPromptViewModel : ViewModel() {
    
    private val _latestPromptResponse = MutableStateFlow<PromptResponse?>(null)
    val latestPromptResponse: StateFlow<PromptResponse?> = _latestPromptResponse.asStateFlow()
    
    private val _selectedPromptTitle = MutableStateFlow<String?>(null)
    val selectedPromptTitle: StateFlow<String?> = _selectedPromptTitle.asStateFlow()

    fun setPromptResponse(response: PromptResponse) {
        _latestPromptResponse.value = response
    }

    fun setSelectedPromptTitle(title: String?) {
        _selectedPromptTitle.value = title
    }

    fun clearSelectedPromptTitle() {
        _selectedPromptTitle.value = null
    }
}