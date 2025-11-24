package com.hariku.feature_journal.domain.model

data class Journal(
    val id: String,
    val userId: String,
    val title: String,
    val date: String,
    val textElements: List<TextElement>,
    val stickerElements: List<StickerElement>,
)