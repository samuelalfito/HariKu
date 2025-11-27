package com.hariku.feature_journal.presentation.create.journal

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.hariku.feature_journal.domain.model.StickerElement
import com.hariku.feature_journal.domain.model.TextElement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class BottomTab {
    TEXT, BACKGROUND, STICKER
}

class CreateJournalViewModel : ViewModel() {
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
    // Tambahkan fungsi event handler lain sesuai kebutuhan
}

