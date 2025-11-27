package com.hariku.feature_journal.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_journal.domain.model.Journal
import com.hariku.feature_journal.domain.usecase.JournalUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class JournalUiState(
    val journals: List<Journal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class JournalViewModel(
    private val useCases: JournalUseCases
) : ViewModel() {

    val tag = "JournalViewModel: "

    private val _uiState = MutableStateFlow(JournalUiState(isLoading = true))
    val uiState: StateFlow<JournalUiState> = _uiState

    init {
        loadJournals()
    }

    fun loadJournals() {
        useCases.getAllJournals()
            .onEach { journals ->
                _uiState.value = _uiState.value.copy(
                    journals = journals,
                    isLoading = false,
                    error = null
                )
//                Log.d(tag, "Loaded journal colors: ${journals[0].backgroundColor}")

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
//    fun loadJournals() {
//        // Atur state ke loading sebelum memulai operasi
//        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
//
//        viewModelScope.launch {
//            try {
//                val journals = useCases.getAllJournals()
//
//                // Perbarui state dengan data yang berhasil dimuat
//                _uiState.value = _uiState.value.copy(
//                    journals = journals,
//                    isLoading = false,
//                    error = null
//                )
//            } catch (e: Exception) {
//                // Tangani error
//                _uiState.value = _uiState.value.copy(
//                    isLoading = false,
//                    error = "Gagal memuat jurnal: ${e.message}"
//                )
//                // Log the error for debugging
//                println("Error loading journals: ${e.message}")
//            }
//        }
//    }

}