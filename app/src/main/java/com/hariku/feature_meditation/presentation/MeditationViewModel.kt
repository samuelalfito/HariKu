package com.hariku.feature_meditation.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_meditation.domain.model.MeditationSongModel
import com.hariku.feature_meditation.domain.model.MeditationState
import com.hariku.feature_meditation.domain.usecase.GetAllMeditationSongsUseCase
import kotlinx.coroutines.launch

class MeditationViewModel(
    private val getAllSongsUseCase: GetAllMeditationSongsUseCase
) : ViewModel() {
    
    var state by mutableStateOf(MeditationState())
        private set
    
    init {
        loadSongs()
    }
    
    fun loadSongs() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            
            getAllSongsUseCase().fold(
                onSuccess = { songs ->
                    state = state.copy(
                        songs = songs,
                        isLoading = false,
                        error = null
                    )
                },
                onFailure = { error ->
                    state = state.copy(
                        isLoading = false,
                        error = error.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }
    
    fun getSongsByCategory(category: String): List<MeditationSongModel> {
        return state.songs.filter { it.category == category }
    }
    
    fun getAllCategories(): List<String> {
        return state.songs.map { it.category }.distinct().sorted()
    }
}