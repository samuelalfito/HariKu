package com.hariku.feature_statistic.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_statistic.domain.model.StatisticData
import com.hariku.feature_statistic.domain.usecase.GetStatisticDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatisticViewModel(
    private val getStatisticDataUseCase: GetStatisticDataUseCase
) : ViewModel() {

    private val _statisticState = MutableStateFlow<StatisticState>(StatisticState.Loading)
    val statisticState: StateFlow<StatisticState> = _statisticState.asStateFlow()

    fun loadStatisticData(userId: String, year: Int, month: Int) {
        viewModelScope.launch {
            try {
                _statisticState.value = StatisticState.Loading
                val data = getStatisticDataUseCase(userId, year, month)
                _statisticState.value = StatisticState.Success(data)
            } catch (e: Exception) {
                _statisticState.value = StatisticState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class StatisticState {
    object Loading : StatisticState()
    data class Success(val data: StatisticData) : StatisticState()
    data class Error(val message: String) : StatisticState()
}

