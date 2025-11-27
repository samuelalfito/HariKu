package com.hariku.feature_journal.domain.model

data class JournalEntry(
    val id: Long = 0,
    val userId: String, // ID user dari Firebase
    val title: String,
    val bgRes: Int,
    val date: String, // Tanggal jurnal dibuat/diubah
    val description: String? = null,
    val stickerElements: List<StickerElement> = emptyList(),
    val textElements: List<TextElement> = emptyList()
)
