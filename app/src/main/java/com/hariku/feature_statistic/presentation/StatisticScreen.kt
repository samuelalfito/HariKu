package com.hariku.feature_statistic.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.hariku.feature_statistic.presentation.components.CalendarView
import com.hariku.feature_statistic.presentation.components.ChartBarCard
import com.hariku.feature_statistic.presentation.components.ChartPieCard
import com.hariku.feature_statistic.presentation.components.StatisticsMood

@Composable
fun StatisticScreen() {
    Scaffold { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                CalendarView()
            }
            item {
                ChartPieCard()
            }
            item {
                StatisticsMood()
            }
            item {
                ChartBarCard()
            }
        }
    }
}