package com.hariku.feature_statistic.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.feature_statistic.domain.model.MoodStatItem

val StatOrangeBg = Color(0xFFFFE0B2)
val StatGreenBg = Color(0xFFC8E6C9)
val StatYellowBg = Color(0xFFFFF9C4)
val StatBlueBg = Color(0xFFBBDEFB)

@Composable
fun StatisticsMood(
    moodStatistics: List<MoodStatItem> = emptyList()
) {
    var expanded by remember { mutableStateOf(true) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f, label = "arrow_rotation"
    )
    
    // Helper function to get mood icon
    fun getMoodIcon(moodType: String): Int {
        return when (moodType) {
            "Senang" -> R.drawable.ic_emote_senang
            "Semangat" -> R.drawable.ic_emote_semangat
            "Biasa" -> R.drawable.ic_emote_biasa
            "Sedih" -> R.drawable.ic_emote_sedih
            "Marah" -> R.drawable.ic_emote_marah
            "Takut" -> R.drawable.ic_emote_takut
            "Cemas" -> R.drawable.ic_emote_cemas
            "Kecewa" -> R.drawable.ic_emote_kecewa
            "Lelah" -> R.drawable.ic_emote_lelah
            "Hampa" -> R.drawable.ic_emote_hampa
            else -> R.drawable.ic_emote_biasa
        }
    }

    // Helper function to get mood background color
    fun getMoodBgColor(index: Int): Color {
        val colors = listOf(StatOrangeBg, StatGreenBg, StatYellowBg, StatBlueBg)
        return colors[index % colors.size]
    }

    // Take top 4 moods
    val topMoods = moodStatistics.take(4)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Statistik",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand/Collapse",
                    modifier = Modifier.rotate(rotationState),
                    tint = Color.Gray
                )
            }
            
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = Color.LightGray.copy(alpha = 0.5f)
            )
            
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (topMoods.isEmpty()) {
                        Text(
                            text = "Belum ada data mood",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        topMoods.forEachIndexed { index, moodStat ->
                            StatisticRowItem(
                                image = getMoodIcon(moodStat.moodType),
                                iconBg = getMoodBgColor(index),
                                label = moodStat.moodType,
                                count = moodStat.count,
                                percentage = "${moodStat.percentage.toInt()}%"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticRowItem(
    image: Int,
    iconBg: Color,
    label: String,
    count: Int = 0,
    percentage: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(iconBg),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "$count kali",
                    fontSize = 12.sp
                )
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = percentage,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticsCardPreview() {
    StatisticsMood()
}