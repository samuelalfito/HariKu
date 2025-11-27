package com.hariku.feature_statistic.data.repository

import com.hariku.feature_home.data.local.MoodDao
import com.hariku.feature_statistic.domain.model.MoodStatItem
import com.hariku.feature_statistic.domain.model.SentimentData
import com.hariku.feature_statistic.domain.model.StatisticData
import com.hariku.feature_statistic.domain.model.WeeklySentiment
import com.hariku.feature_statistic.domain.repository.StatisticRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class StatisticRepositoryImpl(
    private val moodDao: MoodDao
) : StatisticRepository {

    override suspend fun getStatisticData(userId: String, year: Int, month: Int): StatisticData {
        // Get all moods for the user
        val allMoods = moodDao.getAllMoods(userId)

        // Filter moods for the specific month
        val monthMoods = allMoods.filter { mood ->
            val calendar = Calendar.getInstance().apply {
                timeInMillis = mood.timestamp
            }
            calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month
        }

        // Create calendar mood data (day -> moodType)
        val calendarMoodData = monthMoods.associate { mood ->
            val calendar = Calendar.getInstance().apply {
                timeInMillis = mood.timestamp
            }
            calendar.get(Calendar.DAY_OF_MONTH) to mood.moodType
        }

        // Calculate sentiment data based on mood types
        val sentimentData = calculateSentimentData(monthMoods.map { it.moodType })

        // Calculate mood statistics
        val moodStatistics = calculateMoodStatistics(monthMoods.map { it.moodType })

        // Calculate weekly sentiments (normalized to uppercase)
        val weeklySentiments = calculateWeeklySentiments(monthMoods, year, month)

        return StatisticData(
            calendarMoodData = calendarMoodData,
            sentimentData = sentimentData,
            moodStatistics = moodStatistics,
            weeklySentiments = weeklySentiments
        )
    }

    private fun calculateSentimentData(moodTypes: List<String>): SentimentData {
        if (moodTypes.isEmpty()) {
            return SentimentData(0f, 0f, 0f)
        }

        val positiveMoods = listOf("Senang", "Semangat")
        val negativeMoods = listOf("Sedih", "Marah", "Takut", "Cemas", "Kecewa", "Lelah", "Hampa")
        val neutralMoods = listOf("Biasa")

        val positiveCount = moodTypes.count { it in positiveMoods }
        val negativeCount = moodTypes.count { it in negativeMoods }
        val neutralCount = moodTypes.count { it in neutralMoods }

        val total = moodTypes.size.toFloat()

        return SentimentData(
            negative = (negativeCount / total) * 100,
            positive = (positiveCount / total) * 100,
            neutral = (neutralCount / total) * 100
        )
    }

    private fun calculateMoodStatistics(moodTypes: List<String>): List<MoodStatItem> {
        if (moodTypes.isEmpty()) {
            return emptyList()
        }

        val moodCounts = moodTypes.groupingBy { it }.eachCount()
        val total = moodTypes.size.toFloat()

        return moodCounts.map { (moodType, count) ->
            MoodStatItem(
                moodType = moodType,
                count = count,
                percentage = (count / total) * 100
            )
        }.sortedByDescending { it.count }
    }

    private fun calculateWeeklySentiments(
        moods: List<com.hariku.feature_home.data.local.MoodEntity>,
        year: Int,
        month: Int
    ): List<WeeklySentiment> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val weeklySentiments = mutableListOf<WeeklySentiment>()
        val dateFormat = SimpleDateFormat("d MMM", Locale.getDefault())

        val positiveMoods = listOf("Senang", "Semangat")
        val negativeMoods = listOf("Sedih", "Marah", "Takut", "Cemas", "Kecewa", "Lelah", "Hampa")
        val neutralMoods = listOf("Biasa")

        // Group moods by weeks
        var weekStart = calendar.get(Calendar.DAY_OF_MONTH)
        while (weekStart <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            val weekEnd = minOf(weekStart + 6, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))

            val weekMoods = moods.filter { mood ->
                val moodCalendar = Calendar.getInstance().apply {
                    timeInMillis = mood.timestamp
                }
                val day = moodCalendar.get(Calendar.DAY_OF_MONTH)
                day in weekStart..weekEnd
            }

            if (weekMoods.isNotEmpty()) {
                val total = weekMoods.size.toFloat()
                val positiveCount = weekMoods.count { it.moodType in positiveMoods }
                val negativeCount = weekMoods.count { it.moodType in negativeMoods }
                val neutralCount = weekMoods.count { it.moodType in neutralMoods }

                calendar.set(Calendar.DAY_OF_MONTH, weekStart)
                val weekLabel = dateFormat.format(calendar.time)

                weeklySentiments.add(
                    WeeklySentiment(
                        weekLabel = weekLabel,
                        negative = (negativeCount / total) * 100,
                        positive = (positiveCount / total) * 100,
                        neutral = (neutralCount / total) * 100
                    )
                )
            }

            weekStart = weekEnd + 1
        }

        return weeklySentiments
    }
}

