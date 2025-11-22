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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MeditationSongScreen(
    songId: String,
    onNavigateToCompleted: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: MeditationSongViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state
    
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
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.error,
                    color = Color.Red,
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
                    .background(Color(0xFFD3D3D3))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        .background(Color(0xFFFDFCFC))
                        .padding(top = 22.dp, bottom = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = song.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = song.category,
                        fontSize = 12.sp,
                        color = Color.Gray
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
                        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                                color = Color(0xFFD17F4B)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Slider(
                                value = viewModel.currentPosition,
                                onValueChange = { viewModel.seekTo(it) },
                                valueRange = 0f..state.duration,
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFFD17F4B),
                                    activeTrackColor = Color(0xFFD17F4B),
                                    inactiveTrackColor = Color(0xFFE8C5A9)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    formatTime(viewModel.currentPosition.toInt()),
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                                Text(
                                    formatTime(state.duration.toInt()),
                                    color = Color.Gray,
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
                                        tint = Color(0xFFD17F4B),
                                        modifier = Modifier.size(40.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(20.dp))

                                IconButton(
                                    onClick = { viewModel.playPause() },
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(40.dp))
                                        .background(Color(0xFFD17F4B))
                                ) {
                                    Icon(
                                        imageVector = if (state.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = "Play/Pause",
                                        tint = Color.White,
                                        modifier = Modifier.size(48.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(20.dp))

                                IconButton(onClick = { viewModel.skipForward() }) {
                                    Icon(
                                        imageVector = Icons.Default.FastForward,
                                        contentDescription = "Maju 5 detik",
                                        tint = Color(0xFFD17F4B),
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