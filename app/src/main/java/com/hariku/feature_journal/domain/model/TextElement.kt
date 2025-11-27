package com.hariku.feature_journal.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

data class TextElement(
    val id: Long,
    val text: String,
    val offsetX: Float,
    val offsetY: Float,
    val fontSize: Float = 24f,
    val color: Color = Color.White,
    val fontFamily: String = "Default",
    val textAlign: TextAlign = TextAlign.Start,
    val isUnderlined: Boolean = false,
    val shadowX: Float = 0f,
    val shadowY: Float = 0f,
    val shadowRadius: Float = 12f,
    val shadowColor: Color = Color.Black,
    val shadowOpacity: Float = 1f,
    val outlineWidth: Float = 0f,
    val outlineColor: Color = Color.Black,
    val scale: Float = 1f
)