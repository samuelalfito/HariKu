package com.hariku.feature_journal.domain.model

data class StickerElement(
    val id: Long,
    val emoji: String,
    val offsetX: Float,
    val offsetY: Float,
    val scale: Float = 1f,
    val rotation: Float = 0f
)
