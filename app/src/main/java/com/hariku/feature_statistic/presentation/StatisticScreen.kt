package com.hariku.feature_statistic.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Habit(
    val id: Int,
    val name: String,
    val emoji: String,
    val color: Color,
    val completions: MutableMap<LocalDate, Boolean> = mutableMapOf()
)

@Composable
fun StatisticScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    var habits by remember {
        mutableStateOf(
            listOf(
                Habit(1, "Exercise", "💪", Color(0xFFFFE5B4)),
                Habit(2, "Read", "📚", Color(0xFFFFD4D4)),
                Habit(3, "Meditate", "🧘", Color(0xFFD4E5FF)),
                Habit(4, "Water", "💧", Color(0xFFD4F4FF)),
                Habit(5, "Sleep", "😴", Color(0xFFE5D4FF)),
                Habit(6, "Code", "💻", Color(0xFFFFE5D4))
            )
        )
    }
    var showAddDialog by remember { mutableStateOf(false) }

    Column {
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                WarningCard()
            }
            item{
                MoodOverallCard()
            }
        }
    }
}

@Composable
fun WarningCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CardDefaults.shape),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CardDefaults.shape)
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_statistic_warning),
                contentDescription = "Warning Card Logo",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .absoluteOffset(x = -128.dp, y = -128.dp),
                alpha = 0.7f
            )
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PERINGATAN!",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(vertical = 28.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        append("Kami mendeteksi adanya beberapa kalimat yang " +
                            "mengindikasikan potensi adanya perasaan ingin ")

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xffB82B55),
                                fontWeight = FontWeight.Bold
                            )
                        ){
                            append("menyakiti diri")
                        }

                        append(". Ini sangat serius dan perlu ditangani oleh ")

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xffB82B55),
                                fontWeight = FontWeight.Bold
                            )
                        ){
                            append("professional")
                        }

                        append(". Ingatlah untuk menjaga kesehatan mental. Jangan ragu mencari dukungan dari ")

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xffB82B55),
                                fontWeight = FontWeight.Bold
                            )
                        ){
                            append("orang terdekat")
                        }

                        withStyle(
                            style = SpanStyle(
//                                color = Color(0xffB82B55),
                                fontWeight = FontWeight.Bold
                            )
                        ){
                            append(". Banyak yang peduli dan siap membantumu")
                        }

                        append("mengatasi kesulitan yang kamu hadapi.")
                    },
//                        "Kami mendeteksi adanya beberapa kalimat yang " +
//                            "mengindikasikan potensi adanya perasaan ingin " +
//                            "menyakiti diri. Ini sangat serius dan perlu ditangani " +
//                            "oleh profesional. Ingatlah untuk menjaga kesehatan mental. " +
//                            "Jangan ragu mencari dukungan dari orang lain. " +
//                            "Banyak yang peduli dan siap membantumu mengatasi kesulitan " +
//                            "yang kamu hadapi.",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    }
}

@Composable
fun MoodOverallCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CardDefaults.shape),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Box{
            Image(
                painter = painterResource(id = R.drawable.ic_emote_senang),
                contentDescription = "Placeholder logo MoodOverallCard",
                modifier = Modifier
                    .scale(3.5f)
                    .align(Alignment.Center)
                    .absoluteOffset(x = -32.dp, y = 8.dp),
                alpha = 0.2f
            )
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(22.dp))
                Text(
                    text = "32.3% Lebih Bahagia!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFFC97D50)
                )
                Text(
                    text = "Mood Positifmu pada bulan ini Meningkat 32.3% dibanding Bulan Lalu! Bulan ini, kamu sering merasa senang",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                Spacer(modifier = Modifier.height(22.dp))
            }
        }
    }
}

@Composable
fun HabitHomeScreen(
    habits: List<Habit>,
    onToggleCompletion: (Int, LocalDate) -> Unit
) {
    val today = LocalDate.now()
    val startDate = today.minusDays(29)
    val dates = (0..29).map { startDate.plusDays(it.toLong()) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Today - ${today.format(DateTimeFormatter.ofPattern("MMM d"))}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(habits) { habit ->
            HabitCard(habit, dates, today, onToggleCompletion)
        }
    }
}

@Composable
fun HabitCard(
    habit: Habit,
    dates: List<LocalDate>,
    today: LocalDate,
    onToggleCompletion: (Int, LocalDate) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(habit.color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = habit.emoji, fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = habit.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                val streak = calculateStreak(habit, today)
                if (streak > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFFFE5E5))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "🔥",
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$streak",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF6B6B)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                dates.forEach { date ->
                    val isCompleted = habit.completions[date] ?: false
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isCompleted) habit.color
                                else Color(0xFFF0F0F0)
                            )
                            .clickable {
                                if (date <= today) {
                                    onToggleCompletion(habit.id, date)
                                }
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun HabitStatisticsScreen(habits: List<Habit>) {
    val today = LocalDate.now()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Your Progress",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            OverallStatsCard(habits, today)
        }

        item {
            Text(
                text = "Habit Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(habits) { habit ->
            HabitStatsCard(habit, today)
        }
    }
}

@Composable
fun OverallStatsCard(habits: List<Habit>, today: LocalDate) {
    val totalHabits = habits.size
    val completedToday = habits.count { it.completions[today] == true }
    val completionRate = if (totalHabits > 0) {
        (completedToday.toFloat() / totalHabits * 100).toInt()
    } else 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFF6B6B)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Today's Progress",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "$completedToday / $totalHabits",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Habits Completed",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$completionRate%",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun HabitStatsCard(habit: Habit, today: LocalDate) {
    val streak = calculateStreak(habit, today)
    val last30Days = (0..29).count {
        habit.completions[today.minusDays(it.toLong())] == true
    }
    val completionRate = (last30Days / 30f * 100).toInt()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(habit.color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = habit.emoji, fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = habit.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "$completionRate% completion rate",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            if (streak > 0) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "🔥 $streak",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "day streak",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun AddHabitDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, Color) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedEmoji by remember { mutableStateOf("😀") }
    var selectedColor by remember { mutableStateOf(Color(0xFFFFE5B4)) }

    val emojis = listOf(
        "💪", "📚", "🧘", "💧", "😴", "💻", "🎵", "🎨",
        "🏃", "🥗", "📝", "🎮", "📷", "🎯", "⚽", "🎸"
    )

    val colors = listOf(
        Color(0xFFFFE5B4), Color(0xFFFFD4D4), Color(0xFFD4E5FF),
        Color(0xFFD4F4FF), Color(0xFFE5D4FF), Color(0xFFFFE5D4),
        Color(0xFFD4FFD4), Color(0xFFFFD4F4)
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Habit") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Habit Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Select Emoji:", fontWeight = FontWeight.SemiBold)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(8),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(120.dp)
                ) {
                    items(emojis) { emoji ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (emoji == selectedEmoji) Color(0xFFE0E0E0)
                                    else Color.Transparent
                                )
                                .clickable { selectedEmoji = emoji },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = emoji, fontSize = 24.sp)
                        }
                    }
                }

                Text("Select Color:", fontWeight = FontWeight.SemiBold)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    colors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color)
                                .clickable { selectedColor = color }
                        ) {
                            if (color == selectedColor) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .align(Alignment.Center),
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank()) {
                        onAdd(name, selectedEmoji, selectedColor)
                    }
                },
                enabled = name.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

fun calculateStreak(habit: Habit, today: LocalDate): Int {
    var streak = 0
    var currentDate = today

    while (habit.completions[currentDate] == true) {
        streak++
        currentDate = currentDate.minusDays(1)
    }

    return streak
}

val PreviewHabbits = listOf(
    Habit(1, "Exercise", "💪", Color(0xFFFFE5B4)),
    Habit(2, "Read", "📚", Color(0xFFFFD4D4)),
    Habit(3, "Meditate", "🧘", Color(0xFFD4E5FF)),
    Habit(4, "Water", "💧", Color(0xFFD4F4FF)),
    Habit(5, "Sleep", "😴", Color(0xFFE5D4FF)),
    Habit(6, "Code", "💻", Color(0xFFFFE5D4))
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StatisticScreenPreview() {
    StatisticScreen()
}

