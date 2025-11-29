package com.hariku.feature_statistic.di

import com.hariku.feature_statistic.data.repository.StatisticRepositoryImpl
import com.hariku.feature_statistic.domain.repository.StatisticRepository
import com.hariku.feature_statistic.domain.usecase.GetStatisticDataUseCase
import com.hariku.feature_statistic.presentation.StatisticViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val statisticModule = module {

    // Repository
    single<StatisticRepository> {
        StatisticRepositoryImpl(
            moodDao = get()
        )
    }

    // Use Cases
    factoryOf(::GetStatisticDataUseCase)

    // ViewModel
    viewModelOf(::StatisticViewModel)
}

