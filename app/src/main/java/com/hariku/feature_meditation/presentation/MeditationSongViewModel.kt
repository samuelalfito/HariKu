package com.hariku.feature_meditation.presentation

import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_meditation.domain.model.MeditationSongState
import com.hariku.feature_meditation.domain.usecase.GetMeditationSongByIdUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MeditationSongViewModel(
    private val getSongByIdUseCase: GetMeditationSongByIdUseCase
) : ViewModel() {
    
    var state by mutableStateOf(MeditationSongState())
        private set
    
    var currentPosition by mutableFloatStateOf(0f)
        private set
    
    private var updateJob: Job? = null
    private var mediaPlayer: MediaPlayer? = null
    
    fun loadSong(songId: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            
            getSongByIdUseCase(songId).fold(
                onSuccess = { song ->
                    state = state.copy(
                        song = song,
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
    
    fun initializeMediaPlayer(player: MediaPlayer) {
        releasePlayer()
        
        mediaPlayer = player
        state = state.copy(duration = player.duration.toFloat())
        
        player.setOnCompletionListener {
            onSongCompleted()
        }
        
        player.start()
        state = state.copy(isPlaying = true)
        startPositionUpdate()
    }
    
    fun playPause() {
        mediaPlayer?.let { player ->
            if (state.isPlaying) {
                player.pause()
                stopPositionUpdate()
            } else {
                player.start()
                startPositionUpdate()
            }
            state = state.copy(isPlaying = !state.isPlaying)
        }
    }
    
    fun seekTo(position: Float) {
        mediaPlayer?.seekTo(position.toInt())
        currentPosition = position
    }
    
    fun skipForward() {
        mediaPlayer?.let { player ->
            val newPosition = (player.currentPosition + 5000).coerceAtMost(player.duration)
            player.seekTo(newPosition)
            currentPosition = newPosition.toFloat()
        }
    }
    
    fun skipBackward() {
        mediaPlayer?.let { player ->
            val newPosition = (player.currentPosition - 5000).coerceAtLeast(0)
            player.seekTo(newPosition)
            currentPosition = newPosition.toFloat()
        }
    }
    
    private fun startPositionUpdate() {
        updateJob?.cancel()
        
        updateJob = viewModelScope.launch {
            while (isActive && state.isPlaying) {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        currentPosition = it.currentPosition.toFloat()
                    }
                }
                delay(50)
            }
        }
    }
    
    private fun stopPositionUpdate() {
        updateJob?.cancel()
    }
    
    private fun onSongCompleted() {
        state = state.copy(
            isPlaying = false,
            isCompleted = true
        )
        stopPositionUpdate()
    }
    
    fun releasePlayer() {
        stopPositionUpdate()
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
        state = state.copy(isPlaying = false)
        currentPosition = 0f
    }
    
    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }
}