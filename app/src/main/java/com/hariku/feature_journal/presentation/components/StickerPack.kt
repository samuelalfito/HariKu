package com.hariku.feature_journal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StickerPack(
    title: String,
    stickers: List<String>,
    backgroundColor: Color = Color(0xFFFFF8E1),
    onStickerClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (stickers.isNotEmpty()) {
            // Sticker preview grid
            Column(
                modifier = Modifier.width(80.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    stickers.take(2).forEach { sticker ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clickable { onStickerClick(sticker) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = sticker, fontSize = 28.sp)
                        }
                    }
                }
                if (stickers.size > 2) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        stickers.drop(2).take(2).forEach { sticker ->
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable { onStickerClick(sticker) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = sticker, fontSize = 28.sp)
                            }
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF8B5E3C)
        )
    }
}