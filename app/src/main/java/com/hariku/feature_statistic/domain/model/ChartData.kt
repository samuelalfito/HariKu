package com.hariku.feature_statistic.domain.model

import androidx.compose.ui.graphics.Color

data class ChartData(
    val label: String,
    val value: Float,
    val color: Color,
    val displayPercentage: String
)