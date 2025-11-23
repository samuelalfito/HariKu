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
import com.hariku.feature_statistic.presentation.components.CalendarView
import com.hariku.feature_statistic.presentation.components.ChartBarCard
import com.hariku.feature_statistic.presentation.components.ChartPieCard
import com.hariku.feature_statistic.presentation.components.MoodOverallCard
import com.hariku.feature_statistic.presentation.components.WarningCard
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
            item {
                CalendarView()
            }
            item {
                ChartPieCard()
            }
            item {
                ChartBarCard()
            }
        }
    }
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

