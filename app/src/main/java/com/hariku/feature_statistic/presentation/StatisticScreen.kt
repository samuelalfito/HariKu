package com.hariku.feature_statistic.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.hariku.feature_statistic.presentation.components.CalendarView
import com.hariku.feature_statistic.presentation.components.ChartBarCard
import com.hariku.feature_statistic.presentation.components.ChartPieCard
import com.hariku.feature_statistic.presentation.components.MoodOverallCard
import com.hariku.feature_statistic.presentation.components.StatisticsMood
import com.hariku.feature_statistic.presentation.components.WarningCard
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.util.Calendar

data class Habit(
    val id: Int,
    val name: String,
    val emoji: String,
    val color: Color,
    val completions: MutableMap<LocalDate, Boolean> = mutableMapOf()
)

@Composable
fun StatisticScreen(
    viewModel: StatisticViewModel = koinViewModel()
) {
    val statisticState by viewModel.statisticState.collectAsState()

    var currentYear by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    var currentMonth by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.MONTH)) }

    // Get current user ID
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    // Load data when screen is displayed or when month/year changes
    LaunchedEffect(userId, currentYear, currentMonth) {
        if (userId.isNotEmpty()) {
            viewModel.loadStatisticData(userId, currentYear, currentMonth)
        }
    }

    Column {
        Spacer(modifier = Modifier.height(32.dp))

        when (val state = statisticState) {
            is StatisticState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is StatisticState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${state.message}",
                        color = Color.Red
                    )
                }
            }

            is StatisticState.Success -> {
                val data = state.data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        WarningCard()
                    }
                    item {
                        MoodOverallCard()
                    }
                    item {
                        CalendarView(
                            moodData = data.calendarMoodData,
                            currentYear = currentYear,
                            currentMonth = currentMonth,
                            onMonthChange = { year, month ->
                                currentYear = year
                                currentMonth = month
                            }
                        )
                    }
                    item {
                        ChartPieCard(sentimentData = data.sentimentData)
                    }
                    item {
                        StatisticsMood(moodStatistics = data.moodStatistics)
                    }
                    item {
                        ChartBarCard(weeklySentiments = data.weeklySentiments)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StatisticScreenPreview() {
    StatisticScreen()
}
