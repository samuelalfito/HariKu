package com.hariku.feature_journal.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_journal.domain.model.JournalOverview
import com.hariku.feature_journal.domain.model.Note
import com.hariku.feature_journal.domain.usecase.GenerateJournalOverviewUseCase
import com.hariku.feature_journal.domain.usecase.GetJournalOverviewUseCase
import com.hariku.feature_journal.domain.usecase.GetNotesByJournalUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class JournalDetailUiState(
    val notes: List<Note> = emptyList(),
    val overview: JournalOverview? = null,
    val isLoadingNotes: Boolean = false,
    val isGeneratingOverview: Boolean = false,
    val error: String? = null,
    val expandedNoteIds: Set<String> = emptySet()
)

class JournalDetailViewModel(
    private val getNotesByJournalUseCase: GetNotesByJournalUseCase,
    private val getJournalOverviewUseCase: GetJournalOverviewUseCase,
    private val generateJournalOverviewUseCase: GenerateJournalOverviewUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(JournalDetailUiState(isLoadingNotes = true))
    val uiState: StateFlow<JournalDetailUiState> = _uiState.asStateFlow()

    private var currentJournalId: String = ""

    fun loadJournalDetails(journalId: String) {
        currentJournalId = journalId
        
        // Load notes
        viewModelScope.launch {
            getNotesByJournalUseCase(journalId)
                .catch { e ->
                    _uiState.update { it.copy(error = e.message, isLoadingNotes = false) }
                }
                .collect { notes ->
                    _uiState.update {
                        it.copy(
                            notes = notes.sortedByDescending { note -> note.dateTime },
                            isLoadingNotes = false
                        )
                    }
                    
                    // Check if we need to generate overview
                    checkAndGenerateOverview(journalId, notes)
                }
        }
        
        // Load overview
        viewModelScope.launch {
            getJournalOverviewUseCase(journalId)
                .collect { overview ->
                    _uiState.update { it.copy(overview = overview) }
                }
        }
    }

    private suspend fun checkAndGenerateOverview(journalId: String, notes: List<Note>) {
        val currentOverview = _uiState.value.overview
        val noteCount = notes.size
        
        // Generate overview if:
        // 1. No overview exists
        // 2. Note count has changed
        if (currentOverview == null || currentOverview.noteCount != noteCount) {
            if (noteCount > 0) {
                generateOverview(journalId, notes)
            }
        }
    }

    private fun generateOverview(journalId: String, notes: List<Note>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isGeneratingOverview = true) }
            
            val noteContents = notes.map { "${it.title}: ${it.content}" }
            
            val result = generateJournalOverviewUseCase(journalId, noteContents)
            
            result.onSuccess { overview ->
                _uiState.update {
                    it.copy(
                        overview = overview,
                        isGeneratingOverview = false
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        error = error.message,
                        isGeneratingOverview = false
                    )
                }
            }
        }
    }

    fun toggleNoteExpansion(noteId: String) {
        _uiState.update { state ->
            val expandedIds = state.expandedNoteIds.toMutableSet()
            if (expandedIds.contains(noteId)) {
                expandedIds.remove(noteId)
            } else {
                expandedIds.add(noteId)
            }
            state.copy(expandedNoteIds = expandedIds)
        }
    }

    fun isNoteExpanded(noteId: String): Boolean {
        return _uiState.value.expandedNoteIds.contains(noteId)
    }
}

