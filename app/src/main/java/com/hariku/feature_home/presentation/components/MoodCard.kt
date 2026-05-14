package com.hariku.feature_home.presentation.components

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val userId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
    val context = LocalContext.current
    val remainingSecondsState = remember { mutableIntStateOf(0) }
    val remainingSeconds = remainingSecondsState.intValue
    val isInCooldown = remainingSeconds > 0
    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            viewModel.loadTodayMood(userId)
        }
    }
    LaunchedEffect(uiState.lastMoodTimestamp) {
        while (true) {
            remainingSecondsState.intValue = viewModel.getRemainingCooldownSeconds()
            if (remainingSecondsState.intValue <= 0) break
            delay(1000)
        }
    }
    val toastMessage = when {
        uiState.successMessage != null -> uiState.successMessage
        uiState.error != null && !isInCooldown -> uiState.error
        else -> null
    }
    LaunchedEffect(toastMessage) {
        if (!toastMessage.isNullOrBlank()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearMessages()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)) {
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
                    color = if (uiState.todayMood != null) Color(0xFF71A77A) else Color.Black
                )
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
                    val disabled = isInCooldown || uiState.isSaving
                    if (isInCooldown) {
                        Text(
                            text = "Tunggu ${remainingSeconds / 60}m ${remainingSeconds % 60}s untuk submit lagi",
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color(0xFFFF8A7A)
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        moods.chunked(5).forEach { rowMoods ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                rowMoods.forEach { mood ->
                                    MoodItem(
                                        mood = mood,
                                        isSelected = uiState.selectedMoodType == mood.label,
                                        isDisabled = disabled,
                                        isSaving = uiState.isSaving,
                                        onClick = {
                                            if (!disabled) {
                                                viewModel.saveMood(userId, mood.label)
                                            }
                                        }
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
            .background(
                color = if (isSelected) Color(0xFF71A77A) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = !isDisabled && !isSaving, onClick = onClick)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = mood.iconRes),
            contentDescription = mood.label,
            modifier = Modifier.size(38.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = mood.label,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

data class Mood(val label: String, val iconRes: Int)
