package com.hariku.feature_journal.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.hariku.core.ui.theme.Neutral0
import com.hariku.core.ui.theme.Neutral100

data class TextElement(
    val id: Long,
    val text: String,
    val offsetX: Float,
    val offsetY: Float,
    val fontSize: Float = 24f,
    val color: Color = Neutral100,
    val fontFamily: String = "Default",
    val textAlign: TextAlign = TextAlign.Start,
    val isUnderlined: Boolean = false,
    val shadowX: Float = 0f,
    val shadowY: Float = 0f,
    val shadowRadius: Float = 12f,
    val shadowColor: Color = Neutral0,
    val shadowOpacity: Float = 1f,
    val outlineWidth: Float = 0f,
    val outlineColor: Color = Neutral0,
    val scale: Float = 1f
)