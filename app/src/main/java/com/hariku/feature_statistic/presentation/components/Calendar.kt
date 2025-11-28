package com.hariku.feature_statistic.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.hariku.feature_statistic.domain.model.CalendarDay
import java.util.Calendar
import java.util.Locale

private val dayNames = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

private val dividerColor = Color.LightGray.copy(alpha = 0.5f)
private val chevronColor = Color(0xFFBF794E)
private val headerBgColor = Color(0xFFF5F5F5)
private val dayNameColor = Color(0xFF9E9E9E)

@Composable
fun CalendarView(
    moodData: Map<Int, String> = emptyMap(),
    currentYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    currentMonth: Int = Calendar.getInstance().get(Calendar.MONTH),
    onMonthChange: (year: Int, month: Int) -> Unit = { _, _ -> }
) {
    var year by remember(currentYear) { mutableIntStateOf(currentYear) }
    var month by remember(currentMonth) { mutableIntStateOf(currentMonth) }
    var expanded by remember { mutableStateOf(true) }

    fun getMoodFromString(moodType: String): Mood {
        return when (moodType) {
            "Senang" -> Mood.SENANG
            "Semangat" -> Mood.SEMANGAT
            "Biasa" -> Mood.BIASA
            "Sedih" -> Mood.SEDIH
            "Marah" -> Mood.MARAH
            "Takut" -> Mood.TAKUT
            "Cemas" -> Mood.CEMAS
            "Kecewa" -> Mood.KECEWA
            "Lelah" -> Mood.LELAH
            "Hampa" -> Mood.HAMPA
            else -> Mood.NONE
        }
    }

    val calendarData by remember(year, month) {
        derivedStateOf {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, 1)
            }
            val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
            val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) ?: ""
            Triple(daysInMonth, firstDayOfWeek, monthName)
        }
    }
    val (daysInMonth, firstDayOfWeek, monthName) = calendarData

    val calendarCells = remember(daysInMonth, firstDayOfWeek, moodData) {
        val cells = mutableListOf<CalendarDay>()
        repeat(firstDayOfWeek) { cells.add(CalendarDay(day = 0, mood = Mood.NONE, isEmpty = true)) }
        for (day in 1..daysInMonth) {
            val mood = moodData[day]?.let { getMoodFromString(it) } ?: Mood.NONE
            cells.add(CalendarDay(day = day, mood = mood))
        }
        val totalCells = cells.size
        val remainder = totalCells % 7
        if (remainder != 0) {
            repeat(7 - remainder) { cells.add(CalendarDay(day = 0, mood = Mood.NONE, isEmpty = true)) }
        }
        cells
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Suasana Hatimu",
                    fontSize = 17.sp,
                    color = Color.Black
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Toggle",
                    tint = Color.Gray
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp),
                thickness = 1.dp,
                color = dividerColor
            )
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(animationSpec = tween(300)),
                exit = fadeOut() + shrinkVertically(animationSpec = tween(300))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(headerBgColor, RoundedCornerShape(12.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_chevron_left),
                            contentDescription = "Bulan sebelumnya",
                            tint = chevronColor,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    if (month == 0) {
                                        month = 11
                                        year--
                                    } else {
                                        month--
                                    }
                                    onMonthChange(year, month)
                                }
                        )
                        Text(
                            text = "$monthName $year",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_chevron_right),
                            contentDescription = "Bulan berikutnya",
                            tint = chevronColor,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    if (month == 11) {
                                        month = 0
                                        year++
                                    } else {
                                        month++
                                    }
                                    onMonthChange(year, month)
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
                                fontSize = 12.sp,
                                color = dayNameColor,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    val rows = calendarCells.chunked(7)
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        rows.forEach { week ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                week.forEach { cell ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(0.8f)
                                    ) {
                                        if (!cell.isEmpty) {
                                            CalendarDayCell(
                                                day = cell.day,
                                                mood = cell.mood
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
    }
}

@Preview
@Composable
private fun CalendarPreview() {
    CalendarView()
}