package com.hariku.feature_journal.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.hariku.R
import com.hariku.feature_journal.domain.model.StickerElement
import com.hariku.feature_journal.domain.model.TextElement

@Composable
fun JournalCard(
    backgroundColor: Color,
    textElements: List<TextElement>,
    stickerElements: List<StickerElement>,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clickable(onClick = {onClick()})
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
                        .background(Color(0xFF757575))
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
            textElements.forEachIndexed { index, element ->
                EditableText(
                    textElement = element,
                    isSelected = false
                )
            }

            // Sticker elements
            stickerElements.forEachIndexed { index, element ->
                DraggableSticker(
                    stickerElement = element,
                    isSelected = false
                )
            }
        }
    }
}


data class JournalPreviewData(
    val backgroundColor: Color = Color(0xFFF5F5F5),
    val textElements: List<TextElement> = listOf(
        TextElement(
            id=0,
            text = "STRESS",
            offsetX = 100f.toFloat(),
            offsetY = 100f
        ),
        TextElement(
            id=1,
            text = "GAJELAS",
            offsetX = 200f,
            offsetY = 200f,
            color = Color.Black
        )
    ),
    val stickerElements: List<StickerElement> = listOf(StickerElement(
        id=0,
        emoji = "😔",
        offsetX = 200f,
        offsetY = 100f
    )),
)

val journalPreviewData: JournalPreviewData = JournalPreviewData()

@Preview
@Composable
private fun Preview() {
    JournalCard(
        backgroundColor = journalPreviewData.backgroundColor,
        textElements = journalPreviewData.textElements,
        stickerElements = journalPreviewData.stickerElements
    )
}