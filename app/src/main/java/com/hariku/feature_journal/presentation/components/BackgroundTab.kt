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
import com.hariku.core.ui.theme.Blue80
import com.hariku.core.ui.theme.Blue85
import com.hariku.core.ui.theme.Blue90
import com.hariku.core.ui.theme.Coral40
import com.hariku.core.ui.theme.Green50
import com.hariku.core.ui.theme.Green70
import com.hariku.core.ui.theme.Neutral70
import com.hariku.core.ui.theme.Purple50
import com.hariku.core.ui.theme.Purple90
import com.hariku.core.ui.theme.Rose80
import com.hariku.core.ui.theme.Yellow50
import com.hariku.core.ui.theme.Coral90
import com.hariku.core.ui.theme.Purple80

@Composable
fun BackgroundTab(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    val colors = listOf(
        Rose80, Yellow50, Yellow50, Green70,
        Green50, Blue80, Blue85, Blue90,
        Purple90, Purple90, Purple80, Neutral70,
        Coral90, Purple50, Green70,
        // Patterned colors (simulated with gradients)
        Rose80, Blue90, Green70, Purple50,
        Rose80, Yellow50
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
                                Modifier.border(3.dp, Coral40, RoundedCornerShape(8.dp))
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
                containerColor = Coral40
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
