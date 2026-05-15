package com.hariku.feature_meditation.presentation

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import org.koin.androidx.compose.koinViewModel
import com.hariku.core.ui.theme.Coral50
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.Neutral100
import com.hariku.core.ui.theme.Coral90
import com.hariku.core.ui.theme.LocalThemeState
import com.hariku.core.ui.theme.SpecialWarmBeige

@Composable
fun MeditationSongScreen(
    songId: String,
    onNavigateToCompleted: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: MeditationSongViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state
    val isDark = LocalThemeState.current.isDarkTheme
    
    LaunchedEffect(songId) {
        viewModel.loadSong(songId)
    }
    
    LaunchedEffect(state.song) {
        state.song?.let { song ->
            // Untuk sekarang, semua lagu menggunakan meditation_music.mp3
            val mediaPlayer = MediaPlayer.create(context, R.raw.meditation_music)
            viewModel.initializeMediaPlayer(mediaPlayer)
        }
    }
    
    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) {
            onNavigateToCompleted(songId)
        }
    }
    
    DisposableEffect(Unit) {
        onDispose {
            viewModel.releasePlayer()
        }
    }
    
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize().background(AdaptiveColors.adaptiveBackground()),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Coral90)
            }
        }
        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize().background(AdaptiveColors.adaptiveBackground()),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.error,
                    color = Coral90,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        state.song != null -> {
            val song = state.song
            val imageResId = context.resources.getIdentifier(
                song.imageResName,
                "drawable",
                context.packageName
            ).takeIf { it != 0 } ?: R.drawable.cemas_tenangkan_diri
            
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
                        text = song.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AdaptiveColors.adaptiveText()
                    )
                    Text(
                        text = song.category,
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
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = imageResId),
                                contentDescription = song.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = song.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDark) Color(0xFFD88C5A) else Coral50 // Using Orange70 in dark mode
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Slider(
                                value = viewModel.currentPosition,
                                onValueChange = { viewModel.seekTo(it) },
                                valueRange = 0f..state.duration,
                                colors = SliderDefaults.colors(
                                    thumbColor = if (isDark) Color(0xFFD88C5A) else Coral50,
                                    activeTrackColor = if (isDark) Color(0xFFD88C5A) else Coral50,
                                    inactiveTrackColor = if (isDark) Color(0xFF2D2D3A) else SpecialWarmBeige
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    formatTime(viewModel.currentPosition.toInt()),
                                    color = AdaptiveColors.adaptiveTextSecondary(),
                                    fontSize = 12.sp
                                )
                                Text(
                                    formatTime(state.duration.toInt()),
                                    color = AdaptiveColors.adaptiveTextSecondary(),
                                    fontSize = 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(28.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(onClick = { viewModel.skipBackward() }) {
                                    Icon(
                                        imageVector = Icons.Default.FastRewind,
                                        contentDescription = "Mundur 5 detik",
                                        tint = if (isDark) Color(0xFFD88C5A) else Coral50,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(20.dp))

                                IconButton(
                                    onClick = { viewModel.playPause() },
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(40.dp))
                                        .background(if (isDark) Color(0xFFD88C5A) else Coral50)
                                ) {
                                    Icon(
                                        imageVector = if (state.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = "Play/Pause",
                                        tint = Neutral100,
                                        modifier = Modifier.size(48.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(20.dp))

                                IconButton(onClick = { viewModel.skipForward() }) {
                                    Icon(
                                        imageVector = Icons.Default.FastForward,
                                        contentDescription = "Maju 5 detik",
                                        tint = if (isDark) Color(0xFFD88C5A) else Coral50,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun formatTime(ms: Int): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
