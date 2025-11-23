package com.hariku.feature_statistic.domain.model

import com.hariku.feature_statistic.presentation.components.Mood

data class CalendarDay(
    val day: Int,
    val mood: Mood,
    val isEmpty: Boolean = false
)