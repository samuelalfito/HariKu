package com.hariku.feature_meditation.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import org.koin.androidx.compose.koinViewModel
import com.hariku.core.ui.theme.Coral50
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.Neutral100
import com.hariku.core.ui.theme.LocalThemeState

@Composable
fun MeditationSongCompletedScreen(
    songId: String,
    onReturnToMeditationClick: () -> Unit,
    onRepeatClick: (String) -> Unit,
    viewModel: MeditationSongViewModel = koinViewModel()
) {
    val state = viewModel.state
    val isDark = LocalThemeState.current.isDarkTheme
    
    LaunchedEffect(songId) {
        if (state.song == null) {
            viewModel.loadSong(songId)
        }
    }
    
    val primaryColor = if (isDark) Color(0xFFD88C5A) else Coral50 // Orange70 in dark mode
    val buttonCornerRadius = 16.dp
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AdaptiveColors.adaptiveBackground())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(AdaptiveColors.adaptiveCardBackground())
                .padding(top = 22.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.song?.title ?: "Meditasi",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AdaptiveColors.adaptiveText()
            )
            Text(
                text = state.song?.category ?: "Selesai",
                fontSize = 12.sp,
                color = AdaptiveColors.adaptiveTextSecondary()
            )
        }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = AdaptiveColors.adaptiveCardBackground()),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 32.dp, bottom = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.selesai),
                        contentDescription = "Gambar Kucing Selesai",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(240.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Text(
                        text = "Selesai!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AdaptiveColors.adaptiveText(),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Satu langkah lebih dekat dalam mengatasi ${state.song?.category?.lowercase() ?: "masalah"}",
                        fontSize = 18.sp,
                        color = AdaptiveColors.adaptiveTextSecondary(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(40.dp))
                    
                    Button(
                        onClick = onReturnToMeditationClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        shape = RoundedCornerShape(buttonCornerRadius),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                    ) {
                        Text(
                            text = "Kembali ke Halaman Meditasi",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Neutral100
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedButton(
                        onClick = { onRepeatClick(songId) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        shape = RoundedCornerShape(buttonCornerRadius),
                        border = BorderStroke(2.dp, primaryColor),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = primaryColor,
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Ulangi Kembali",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
