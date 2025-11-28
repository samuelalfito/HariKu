package com.hariku.feature_statistic.domain.model

data class StatisticData(
    val calendarMoodData: Map<Int, String>, // day -> moodType
    val sentimentData: SentimentData,
    val moodStatistics: List<MoodStatItem>,
    val weeklySentiments: List<WeeklySentiment>
)

data class SentimentData(
    val negative: Float,
    val positive: Float,
    val neutral: Float
)

data class MoodStatItem(
    val moodType: String,
    val count: Int,
    val percentage: Float
)

data class WeeklySentiment(
    val weekLabel: String,
    val negative: Float,
    val positive: Float,
    val neutral: Float
)

