package com.hariku.feature_journal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hariku.feature_journal.domain.model.JournalEntry
import com.hariku.feature_journal.domain.usecase.JournalUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

// Model State UI
data class JournalUiState(
    val journals: List<JournalEntry> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class JournalViewModel(
    private val useCases: JournalUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(JournalUiState(isLoading = true))
    val uiState: StateFlow<JournalUiState> = _uiState

    private val currentUserId = getUserId()

    init {
        // Otomatis memuat jurnal saat ViewModel dibuat
        loadJournals()
    }

    /**
     * Memuat semua jurnal dari database lokal untuk user saat ini.
     * Data dimuat menggunakan Flow agar real-time (perubahan Room langsung tercermin di UI).
     */
    fun loadJournals() {
        useCases.getAllJournals(currentUserId)
            .onEach { journals ->
                _uiState.value = _uiState.value.copy(
                    journals = journals,
                    isLoading = false,
                    error = null
                )
            }
            .catch { e ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Gagal memuat jurnal: ${e.message}"
                )
                // Log the error for debugging
                println("Error loading journals: ${e.message}")
            }
            .launchIn(viewModelScope) // Menggunakan launchIn untuk Flow
    }

    /**
     * Menyimpan atau memperbarui sebuah jurnal.
     */
    fun saveJournal(journal: JournalEntry) = viewModelScope.launch {
        try {
            // Pastikan journal memiliki userId yang benar
            val journalToSave = journal.copy(userId = currentUserId)
            val newId = useCases.saveJournal(journalToSave)
            println("Jurnal berhasil disimpan dengan ID: $newId")
            // Karena kita menggunakan Flow, daftar jurnal akan otomatis diperbarui.
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                error = "Gagal menyimpan jurnal: ${e.message}"
            )
        }
    }

    /**
     * Menghapus jurnal.
     */
    fun deleteJournal(journalId: Long) = viewModelScope.launch {
        try {
            useCases.deleteJournal(journalId)
            println("Jurnal ID $journalId berhasil dihapus.")
            // Daftar jurnal akan otomatis diperbarui oleh Flow.
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                error = "Gagal menghapus jurnal: ${e.message}"
            )
        }
    }

    fun getUserId(): String = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
}