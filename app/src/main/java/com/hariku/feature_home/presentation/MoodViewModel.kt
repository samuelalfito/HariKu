package com.hariku.feature_home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_auth.domain.model.AuthUser
import com.hariku.feature_auth.domain.usecase.GetCurrentUserUseCase
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
    private val getTodayMoodUseCase: GetTodayMoodUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase // USE CASE BARU untuk ngambil user
) : ViewModel() {

    var uiState by mutableStateOf(MoodUiState())
        private set

    fun getCurrentUser(): AuthUser? { // CONTOH: getCurrentUser().uid
        return getCurrentUserUseCase()
    }

    fun loadTodayMood(userId: String) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                val todayMood = getTodayMoodUseCase(userId)
                uiState = uiState.copy(
                    todayMood = todayMood,
                    isLoading = false,
                    selectedMoodType = todayMood?.moodType
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
                // Check if already submitted today
                if (uiState.todayMood != null) {
                    uiState = uiState.copy(error = "Kamu sudah submit mood hari ini!")
                    return@launch
                }

                uiState = uiState.copy(isSaving = true, error = null)

                val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val mood = MoodModel(
                    id = UUID.randomUUID().toString(),
                    userId = userId,
                    moodType = moodType, // Keep Title Case format like "Senang", "Sedih"
                    date = today,
                    timestamp = System.currentTimeMillis()
                )

                val result = saveMoodUseCase(mood)
                
                if (result.isSuccess) {
                    uiState = uiState.copy(
                        isSaving = false,
                        todayMood = mood,
                        selectedMoodType = moodType,
                        successMessage = "Mood berhasil disimpan! 🎉"
                    )
                } else {
                    uiState = uiState.copy(
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
}

data class MoodUiState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val todayMood: MoodModel? = null,
    val selectedMoodType: String? = null,
    val error: String? = null,
    val successMessage: String? = null
)

