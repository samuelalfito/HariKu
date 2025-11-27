package com.hariku.feature_journal.domain.model

import androidx.compose.ui.graphics.Color

data class Journal(
    val id: String,
    val userId: String,
    val title: String,
    val date: String,
    val backgroundColor: Color,
    val textElements: List<TextElement>,
    val stickerElements: List<StickerElement>,
)