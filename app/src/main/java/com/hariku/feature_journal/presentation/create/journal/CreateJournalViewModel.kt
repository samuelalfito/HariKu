package com.hariku.feature_journal.presentation.create.journal

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_auth.domain.usecase.GetCurrentUserUseCase
import com.hariku.feature_journal.domain.model.Journal
import com.hariku.feature_journal.domain.model.StickerElement
import com.hariku.feature_journal.domain.model.TextElement
import com.hariku.feature_journal.domain.usecase.JournalUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class BottomTab {
    TEXT, BACKGROUND, STICKER
}

class CreateJournalViewModel(
    private val useCases: JournalUseCases,
    getCurrentUserUseCase: GetCurrentUserUseCase,
) : ViewModel() {
    
    val tag = "CreateJournalViewModel: "
    val currentUser = getCurrentUserUseCase()
    private val _selectedTab = MutableStateFlow(BottomTab.TEXT)
    val selectedTab: StateFlow<BottomTab> = _selectedTab
    
    private val _notebookBackground = MutableStateFlow(Color(0xFFAAAAAA))
    val notebookBackground: StateFlow<Color> = _notebookBackground
    
    private val _textElements = MutableStateFlow<List<TextElement>>(emptyList())
    val textElements: StateFlow<List<TextElement>> = _textElements
    
    private val _stickerElements = MutableStateFlow<List<StickerElement>>(emptyList())
    val stickerElements: StateFlow<List<StickerElement>> = _stickerElements
    
    private val _selectedTextIndex = MutableStateFlow<Int?>(null)
    val selectedTextIndex: StateFlow<Int?> = _selectedTextIndex
    
    private val _selectedStickerIndex = MutableStateFlow<Int?>(null)
    val selectedStickerIndex: StateFlow<Int?> = _selectedStickerIndex
    
    fun setSelectedTab(tab: BottomTab) {
        _selectedTab.value = tab
    }
    
    fun setNotebookBackground(color: Color) {
        _notebookBackground.value = color
    }
    
    fun setTextElements(elements: List<TextElement>) {
        _textElements.value = elements
        if (!elements.isEmpty()) {
            Log.d(
                tag,
                "setTextElements, OffsetX: ${elements[0].offsetX} OffsetY: ${elements[0].offsetY}"
            )
        }
    }
    
    fun setStickerElements(elements: List<StickerElement>) {
        _stickerElements.value = elements
    }
    
    fun setSelectedTextIndex(index: Int?) {
        _selectedTextIndex.value = index
    }
    
    fun setSelectedStickerIndex(index: Int?) {
        _selectedStickerIndex.value = index
    }
    
    fun saveJournal() = viewModelScope.launch {
        val currentUserId = currentUser!!.uid
        
        val newJournalId = "" // Karna baru, idnya kosong nanti dibuat di repo
        
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        
        var title = ""
        textElements.value.forEach { title += (" " + it.text) }
        
        val journal = Journal(
            id = newJournalId,
            userId = currentUserId,
            title = title,
            date = currentDate,
            textElements = _textElements.value.map {
                it.copy(
                    offsetX = it.offsetX / 3,
                    offsetY = it.offsetY / 2.5.toFloat()
                )
            },
            stickerElements = _stickerElements.value.map {
                it.copy(
                    offsetX = it.offsetX / 3,
                    offsetY = it.offsetY / 2.5.toFloat()
                )
            },
            backgroundColor = _notebookBackground.value
        )
        
        try {
            useCases.saveJournal(journal)
        } catch (e: Exception) {
            Log.e(tag, "Error saving journal: ${e.message}")
        }
    }
}