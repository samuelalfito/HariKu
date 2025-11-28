package com.hariku.feature_home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.hariku.R
import com.hariku.feature_home.presentation.MoodViewModel
import kotlinx.coroutines.delay

@Composable
fun MoodCard(
    viewModel: MoodViewModel,
) {
    val uiState = viewModel.uiState
    
    // Get actual userId from Firebase Auth
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    
    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            viewModel.loadTodayMood(userId)
        }
    }
    
    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            delay(3000)
            viewModel.clearMessages()
        }
    }
    
    var remainingSeconds by remember { mutableIntStateOf(0) }
    
    LaunchedEffect(uiState.lastMoodTimestamp) {
        while (true) {
            remainingSeconds = viewModel.getRemainingCooldownSeconds()
            if (remainingSeconds <= 0) break
            delay(1000)
        }
    }
    
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            val isInCooldown = remainingSeconds > 0
            val minutes = remainingSeconds / 60
            val seconds = remainingSeconds % 60
            
            Text(
                text = if (uiState.todayMood != null) {
                    "Mood Terakhir: ${uiState.todayMood.moodType}"
                } else {
                    "Bagaimana Suasana Hatimu Hari Ini?"
                },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = when {
                    uiState.todayMood != null -> Color(0xFF71a77a)
                    else -> Color.Black
                }
            )
            
            if (uiState.error != null && !isInCooldown) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.error,
                    fontSize = 12.sp,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            if (uiState.successMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.successMessage,
                    fontSize = 12.sp,
                    color = Color(0xFF71a77a),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            } else {
                val moods = listOf(
                    Mood("Senang", R.drawable.ic_emote_senang),
                    Mood("Biasa", R.drawable.ic_emote_biasa),
                    Mood("Sedih", R.drawable.ic_emote_sedih),
                    Mood("Marah", R.drawable.ic_emote_marah),
                    Mood("Cemas", R.drawable.ic_emote_cemas),
                    Mood("Lelah", R.drawable.ic_emote_lelah),
                    Mood("Kecewa", R.drawable.ic_emote_kecewa),
                    Mood("Takut", R.drawable.ic_emote_takut),
                    Mood("Hampa", R.drawable.ic_emote_hampa),
                    Mood("Semangat", R.drawable.ic_emote_semangat)
                )
                
                val isDisabled = isInCooldown || uiState.isSaving
                
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        moods.take(5).forEach { mood ->
                            MoodItem(
                                mood = mood,
                                isSelected = uiState.selectedMoodType == mood.label,
                                isDisabled = isDisabled,
                                isSaving = uiState.isSaving,
                                onClick = {
                                    if (!isDisabled) {
                                        viewModel.saveMood(userId, mood.label)
                                    }
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        moods.drop(5).forEach { mood ->
                            MoodItem(
                                mood = mood,
                                isSelected = uiState.selectedMoodType == mood.label,
                                isDisabled = isDisabled,
                                isSaving = uiState.isSaving,
                                onClick = {
                                    if (!isDisabled) {
                                        viewModel.saveMood(userId, mood.label)
                                    }
                                }
                            )
                        }
                    }
                }
                if (isInCooldown) {
                    Text(
                        text = "Tunggu ${minutes}m ${seconds}s untuk submit lagi",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFF8A7A)
                    )
                }
            }
        }
    }
}

@Composable
private fun MoodItem(
    mood: Mood,
    isSelected: Boolean,
    isDisabled: Boolean,
    isSaving: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .alpha(if (isDisabled && !isSelected) 0.4f else 1f)
            .clickable(enabled = !isDisabled && !isSaving, onClick = onClick)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color(0xFF71a77a).copy(alpha = 0.2f), CircleShape)
                        .border(3.dp, Color(0xFF71a77a), CircleShape)
                )
            }
            Image(
                painter = painterResource(id = mood.iconRes),
                contentDescription = mood.label,
                modifier = Modifier.size(38.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = mood.label,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color(0xFF71a77a) else Color.Black
        )
    }
}

data class Mood(val label: String, val iconRes: Int)