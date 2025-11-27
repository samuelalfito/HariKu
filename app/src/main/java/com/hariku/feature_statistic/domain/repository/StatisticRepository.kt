package com.hariku.feature_statistic.domain.repository

import com.hariku.feature_statistic.domain.model.StatisticData

interface StatisticRepository {
    suspend fun getStatisticData(userId: String, year: Int, month: Int): StatisticData
}

