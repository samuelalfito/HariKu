package com.hariku.feature_journal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
fun BackgroundTab(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    val colors = listOf(
        Color(0xFFFFB3BA), Color(0xFFFFD4A3), Color(0xFFFFE5A3), Color(0xFFB8E6B8),
        Color(0xFF9FE2BF), Color(0xFF87CEEB), Color(0xFF89CFF0), Color(0xFF6495ED),
        Color(0xFF7B68EE), Color(0xFFDA70D6), Color(0xFFDDA0DD), Color(0xFF757575),
        Color(0xFFFF9A76), Color(0xFFB19CD9), Color(0xFFA3E4D7),
        // Patterned colors (simulated with gradients)
        Color(0xFFFFB3BA), Color(0xFF6495ED), Color(0xFFA3E4D7), Color(0xFFB19CD9),
        Color(0xFFFFB3C1), Color(0xFFFFD4A3)
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(colors.size) { index ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors[index])
                        .clickable { onColorSelected(colors[index]) }
                        .then(
                            if (colors[index] == selectedColor)
                                Modifier.border(3.dp, Color(0xFFCF6D49), RoundedCornerShape(8.dp))
                            else Modifier
                        )
                ) {
                    // Add pattern/texture here for last row items if needed
                    if (index >= colors.size - 7 && index % 7 == 6) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(24.dp)
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Add image button
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
                text = "Tambah Gambar",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}
