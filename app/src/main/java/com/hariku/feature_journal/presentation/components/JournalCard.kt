package com.hariku.feature_journal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.hariku.feature_journal.domain.model.StickerElement
import com.hariku.feature_journal.domain.model.TextElement
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.LocalThemeState

@Composable
fun JournalCard(
    backgroundColor: Color,
    textElements: List<TextElement>,
    stickerElements: List<StickerElement>,
    onClick: () -> Unit = {}
) {
    val isDark = LocalThemeState.current.isDarkTheme
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .height(160.dp)
    ) {
        // Spiral binding
        Column(
            modifier = Modifier
                .width(40.dp)
                .fillMaxHeight()
                .zIndex(2f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(11) {
                Box(
                    modifier = Modifier
                        .size(width = 28.dp, height = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isDark) Color(0xFF2D2D3A) else Color(0xFF7A7A7A)) // Neutral30 or Neutral75
                )
            }
        }

        // Notebook
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .padding(start = 16.dp)
                .background(backgroundColor)
        ) {
            // Text elements
            textElements.forEach { element ->
                EditableText(
                    textElement = element,
                    isSelected = false
                )
            }

            // Sticker elements
            stickerElements.forEach { element ->
                DraggableSticker(
                    stickerElement = element,
                    isSelected = false
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    JournalCard(
        backgroundColor = Color(0xFFFDE8D8),
        textElements = listOf(
            TextElement(id = 1, text = "Jurnal Hari Ini", offsetX = 20f, offsetY = 20f, color = Color.Black)
        ),
        stickerElements = listOf(
            StickerElement(id = 1, emoji = "😊", offsetX = 100f, offsetY = 50f)
        )
    )
}
