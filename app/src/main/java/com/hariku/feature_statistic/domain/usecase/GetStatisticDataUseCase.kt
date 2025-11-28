package com.hariku.feature_statistic.domain.usecase

import com.hariku.feature_statistic.domain.model.StatisticData
import com.hariku.feature_statistic.domain.repository.StatisticRepository

class GetStatisticDataUseCase(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(userId: String, year: Int, month: Int): StatisticData {
        return repository.getStatisticData(userId, year, month)
    }
}

