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
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import com.hariku.R

@Composable
fun MeditationSongScreen() {
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.meditation_music) }

    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0f) }
    val duration = remember { mediaPlayer.duration.toFloat() }

    LaunchedEffect(isPlaying) {
        while (isActive && isPlaying) {
            currentPosition = mediaPlayer.currentPosition.toFloat()
            delay(100)
        }
    }

    DisposableEffect(Unit) {
        onDispose { mediaPlayer.release() }
    }

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
                text = "Mengapa Cemas",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Text(
                text = "Meditasi",
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
                modifier = Modifier
                    .fillMaxWidth(),
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
                        painter = painterResource(id = R.drawable.cemas_mengapa_cemas),
                        contentDescription = "Cover Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Peaceful Mind",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD17F4B)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Slider(
                        value = currentPosition,
                        onValueChange = {
                            currentPosition = it
                        },
                        onValueChangeFinished = {
                            mediaPlayer.seekTo(currentPosition.toInt())
                        },
                        valueRange = 0f..duration,
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
                        Text(formatTime(currentPosition.toInt()), color = Color.Gray, fontSize = 12.sp)
                        Text(formatTime(duration.toInt()), color = Color.Gray, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = {
                            val newPosition = (mediaPlayer.currentPosition - 5000).coerceAtLeast(0)
                            mediaPlayer.seekTo(newPosition)
                            currentPosition = newPosition.toFloat()
                        }) {
                            Icon(
                                imageVector = Icons.Default.FastRewind,
                                contentDescription = "Rewind 5 detik",
                                tint = Color(0xFFD17F4B),
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        IconButton(
                            onClick = {
                                if (isPlaying) mediaPlayer.pause() else mediaPlayer.start()
                                isPlaying = !isPlaying
                            },
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(40.dp))
                                .background(Color(0xFFD17F4B))
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play/Pause",
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        IconButton(onClick = {
                            val newPosition = (mediaPlayer.currentPosition + 5000)
                                .coerceAtMost(mediaPlayer.duration)
                            mediaPlayer.seekTo(newPosition)
                            currentPosition = newPosition.toFloat()
                        }) {
                            Icon(
                                imageVector = Icons.Default.FastForward,
                                contentDescription = "Forward 5 detik",
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

fun formatTime(ms: Int): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
