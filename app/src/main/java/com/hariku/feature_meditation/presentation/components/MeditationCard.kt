package com.hariku.feature_meditation.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R

@Composable
fun MeditationCard(
    song: com.hariku.feature_meditation.domain.model.MeditationSongModel,
    context: android.content.Context,
    onClick: () -> Unit
) {
    val imageResId = context.resources.getIdentifier(
        song.imageResName,
        "drawable",
        context.packageName
    ).takeIf { it != 0 } ?: R.drawable.cemas_tenangkan_diri
    
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .width(120.dp)
            .padding(end = 12.dp)
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = song.title,
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 4.dp)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = song.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF242424)
            )
        }
    }
}