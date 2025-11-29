package com.hariku.feature_journal.presentation.create.note

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_auth.domain.usecase.GetCurrentUserUseCase
import com.hariku.feature_journal.domain.model.Note
import com.hariku.feature_journal.domain.usecase.SaveNoteUseCase
import kotlinx.coroutines.launch
import java.util.UUID

class CreateNoteViewModel(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    var selectedJournalId by mutableStateOf("")
        private set

    var selectedJournalTitle by mutableStateOf("")
        private set

    var selectedDateTime by mutableStateOf(System.currentTimeMillis())
        private set

    val imageUris = mutableStateListOf<Uri>()

    var isSaving by mutableStateOf(false)
        private set

    var saveSuccess by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun updateTitle(newTitle: String) {
        if (newTitle.length <= 60) {
            title = newTitle
        }
    }

    fun updateContent(newContent: String) {
        if (newContent.length <= 50000) {
            content = newContent
        }
    }

    fun selectJournal(journalId: String, journalTitle: String) {
        selectedJournalId = journalId
        selectedJournalTitle = journalTitle
    }

    fun updateDateTime(timestamp: Long) {
        selectedDateTime = timestamp
    }

    fun addImage(uri: Uri) {
        if (imageUris.size < 3) {
            imageUris.add(uri)
        }
    }

    fun removeImage(uri: Uri) {
        imageUris.remove(uri)
    }

    fun saveNote(imageUrls: List<String> = emptyList()) {
        viewModelScope.launch {
            try {
                isSaving = true
                errorMessage = null

                val userId = getCurrentUserUseCase()?.uid ?: ""
                
                val note = Note(
                    id = UUID.randomUUID().toString(),
                    journalId = selectedJournalId,
                    userId = userId,
                    title = title,
                    content = content,
                    imageUrls = imageUrls,
                    dateTime = selectedDateTime,
                    createdAt = System.currentTimeMillis()
                )

                val result = saveNoteUseCase(note)
                
                result.onSuccess {
                    saveSuccess = true
                }.onFailure { error ->
                    errorMessage = error.message ?: "Gagal menyimpan catatan"
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Terjadi kesalahan"
            } finally {
                isSaving = false
            }
        }
    }

    fun resetState() {
        title = ""
        content = ""
        selectedJournalId = ""
        selectedJournalTitle = ""
        selectedDateTime = System.currentTimeMillis()
        imageUris.clear()
        saveSuccess = false
        errorMessage = null
    }

    fun canSave(): Boolean {
        return title.isNotBlank() &&
               content.isNotBlank() &&
               selectedJournalId.isNotBlank() &&
               !isSaving
    }

    fun prefillTitle(newTitle: String) {
        if (title.isBlank()) {
            title = newTitle.take(60)
        }
    }
}
