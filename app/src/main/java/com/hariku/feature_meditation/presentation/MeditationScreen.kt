package com.hariku.feature_meditation.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.feature_meditation.presentation.components.CategorySection
import org.koin.androidx.compose.koinViewModel

@Composable
fun MeditationScreen(
    onNavigateToSong: (String) -> Unit,
    viewModel: MeditationViewModel = koinViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    
    LaunchedEffect(Unit) {
        if (state.songs.isEmpty() && !state.isLoading) {
            viewModel.loadSongs()
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.error != null -> {
                Text(
                    text = state.error,
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    item {
                        Text(
                            text = "Panduan Meditasi",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF242424),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                    
                    val categories = viewModel.getAllCategories()
                    items(categories.size) { index ->
                        val category = categories[index]
                        val songs = viewModel.getSongsByCategory(category)
                        
                        CategorySection(
                            title = category,
                            songs = songs,
                            context = context,
                            onSongClick = { songId ->
                                onNavigateToSong(songId)
                            }
                        )
                    }
                }
            }
        }
    }
}