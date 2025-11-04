package com.hariku.feature_statistic.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import java.util.Calendar

enum class Mood(val iconRes: Int, val color: Color) {
    SENANG(R.drawable.ic_emote_senang, Color(0xFFFFD481)),
    SEMANGAT(R.drawable.ic_emote_semangat, Color(0xFFFFCEA2)),
    BIASA(R.drawable.ic_emote_biasa, Color(0xFFE9C9AC)),
    SEDIH(R.drawable.ic_emote_sedih, Color(0xFFB0E6ED)),
    MARAH(R.drawable.ic_emote_marah, Color(0xFFF1A69A)),
    TAKUT(R.drawable.ic_emote_takut, Color(0xFFADDBB5)),
    CEMAS(R.drawable.ic_emote_cemas, Color(0xFFD5C5F5)),
    KECEWA(R.drawable.ic_emote_kecewa, Color(0xFFD1EDAE)),
    LELAH(R.drawable.ic_emote_lelah, Color(0xFFE9CAEC)),
    HAMPA(R.drawable.ic_emote_hampa, Color(0xFFDBE0DF)),
    NONE(R.drawable.ic_cross, Color(0xFF9F9F9F))
}

val dayNames = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

@Composable
fun CalendarView() {
    var calendar by remember { mutableStateOf(Calendar.getInstance()) }
    var isExpanded by remember { mutableStateOf(true) }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, 1)
    }.get(Calendar.DAY_OF_WEEK) - 1
    
    val moodMap = remember {
        mutableMapOf<Int, Mood>().apply {
            put(1, Mood.SENANG)
            put(2, Mood.BIASA)
            put(3, Mood.MARAH)
            put(4, Mood.SENANG)
            put(5, Mood.KECEWA)
            put(6, Mood.SEDIH)
            put(7, Mood.SEDIH)
            put(8, Mood.SENANG)
            put(9, Mood.SENANG)
            put(10, Mood.CEMAS)
            put(11, Mood.SEDIH)
            put(12, Mood.BIASA)
            put(13, Mood.KECEWA)
            put(14, Mood.CEMAS)
            put(15, Mood.SEDIH)
            put(16, Mood.KECEWA)
            put(17, Mood.NONE)
            put(18, Mood.MARAH)
            put(19, Mood.BIASA)
            put(20, Mood.MARAH)
            put(21, Mood.CEMAS)
            put(22, Mood.BIASA)
            put(23, Mood.SENANG)
            put(24, Mood.KECEWA)
            put(25, Mood.KECEWA)
            put(26, Mood.CEMAS)
            put(27, Mood.SENANG)
            put(28, Mood.BIASA)
            put(29, Mood.SENANG)
            put(30, Mood.BIASA)
        }
    }
    
    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Suasana Hatimu",
                    fontSize = 17.sp,
                    color = Color.Black
                )
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        painter = painterResource(
                            if (isExpanded) R.drawable.ic_chevron_up
                            else R.drawable.ic_chevron_down
                        ),
                        contentDescription = if (isExpanded) "Sembunyikan" else "Tampilkan",
                        tint = Color(0xFF9E9E9E)
                    )
                }
            }
            
            if (isExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFE0E0E0))
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_chevron_left),
                        contentDescription = "Bulan sebelumnya",
                        tint = Color(0xFFBF794E),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                calendar = Calendar
                                    .getInstance()
                                    .apply {
                                        set(Calendar.YEAR, year)
                                        set(Calendar.MONTH, month - 1)
                                    }
                            }
                    )
                    Text(
                        text = "${
                            calendar.getDisplayName(
                                Calendar.MONTH,
                                Calendar.LONG,
                                java.util.Locale.ENGLISH
                            )
                        } $year",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_chevron_right),
                        contentDescription = "Bulan berikutnya",
                        tint = Color(0xFFBF794E),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                calendar = Calendar
                                    .getInstance()
                                    .apply {
                                        set(Calendar.YEAR, year)
                                        set(Calendar.MONTH, month + 1)
                                    }
                            }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    dayNames.forEach { day ->
                        Text(
                            text = day,
                            color = Color(0xFF9E9E9E),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                
                val totalCells = daysInMonth + firstDayOfWeek
                val rows = (totalCells + 6) / 7
                
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    var day = 1
                    for (row in 0 until rows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for (col in 0..6) {
                                val cellIndex = row * 7 + col
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(0.8f)
                                        .padding(2.dp)
                                ) {
                                    if (cellIndex >= firstDayOfWeek && day <= daysInMonth) {
                                        val mood = moodMap[day] ?: Mood.NONE
                                        CalendarDayCell(day = day, mood = mood)
                                        day++
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CalendarPreview() {
    CalendarView()
}