package com.hariku.feature_home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_home.domain.model.MoodModel
import com.hariku.feature_home.domain.usecase.GetTodayMoodUseCase
import com.hariku.feature_home.domain.usecase.SaveMoodUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class MoodViewModel(
    private val saveMoodUseCase: SaveMoodUseCase,
    private val getTodayMoodUseCase: GetTodayMoodUseCase
) : ViewModel() {

    var uiState by mutableStateOf(MoodUiState())
        private set

    fun loadTodayMood(userId: String) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                val todayMood = getTodayMoodUseCase(userId)
                uiState = uiState.copy(
                    todayMood = todayMood,
                    isLoading = false,
                    selectedMoodType = todayMood?.moodType,
                    lastMoodTimestamp = todayMood?.timestamp
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun saveMood(userId: String, moodType: String) {
        viewModelScope.launch {
            try {
                val currentTime = System.currentTimeMillis()
                val lastMoodTime = uiState.lastMoodTimestamp
                val fiveMinutesInMillis = 5 * 60 * 1000
                
                if (lastMoodTime != null && (currentTime - lastMoodTime) < fiveMinutesInMillis) {
                    return@launch
                }

                uiState = uiState.copy(isSaving = true, error = null)

                val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val mood = MoodModel(
                    id = UUID.randomUUID().toString(),
                    userId = userId,
                    moodType = moodType,
                    date = today,
                    timestamp = currentTime
                )

                val result = saveMoodUseCase(mood)
                
                uiState = if (result.isSuccess) {
                    uiState.copy(
                        isSaving = false,
                        todayMood = mood,
                        selectedMoodType = moodType,
                        lastMoodTimestamp = currentTime,
                        successMessage = "Mood berhasil disimpan!"
                    )
                } else {
                    uiState.copy(
                        isSaving = false,
                        error = "Gagal menyimpan mood"
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isSaving = false,
                    error = e.message
                )
            }
        }
    }

    fun clearMessages() {
        uiState = uiState.copy(error = null, successMessage = null)
    }

    fun getRemainingCooldownSeconds(): Int {
        val lastMoodTime = uiState.lastMoodTimestamp ?: return 0
        val currentTime = System.currentTimeMillis()
        val fiveMinutesInMillis = 5 * 60 * 1000
        val remaining = fiveMinutesInMillis - (currentTime - lastMoodTime)
        return if (remaining > 0) (remaining / 1000).toInt() else 0
    }
}

data class MoodUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val todayMood: MoodModel? = null,
    val selectedMoodType: String? = null,
    val lastMoodTimestamp: Long? = null,
    val error: String? = null,
    val successMessage: String? = null
)

