package com.hariku.feature_journal.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StickerTab(onStickerSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                StickerPack(
                    title = "Cats Sticker Pack",
                    stickers = listOf("😺", "😸", "😹", "😻", "😼", "😽"),
                    onStickerClick = onStickerSelected
                )
            }
            item {
                StickerPack(
                    title = "Activity Illustrations Sticker Pack",
                    backgroundColor = Color(0xFFFFB3D9),
                    stickers = listOf("⏰", "⏰"),
                    onStickerClick = onStickerSelected
                )
            }
            item {
                StickerPack(
                    title = "Tasha The Bear Stickers",
                    backgroundColor = Color(0xFF9FD6A8),
                    stickers = emptyList(),
                    onStickerClick = onStickerSelected
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Add sticker button
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFCF6D49)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Tambah Sticker Gambar",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}