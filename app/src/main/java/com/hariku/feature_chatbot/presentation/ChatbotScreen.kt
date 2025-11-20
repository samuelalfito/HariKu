package com.hariku.feature_chatbot.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.core.ui.components.FloatingActButton
import com.hariku.core.ui.components.Routes
import com.hariku.core.ui.components.SosTopBar
import com.hariku.feature_chatbot.presentation.components.ChatItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatbotScreen(
    navController: NavController,
    viewModel: ChatbotViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle error state
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(error)
        }
    }

    Scaffold(
        containerColor = Color(0xFFF9F9F9),
        topBar = {
            SosTopBar(
                title = "Obrolan",
                onSosClick = { navController.navigate(Routes.SosGraph.route) }
            )
        },
        floatingActionButton = {
            FloatingActButton(
                label = "Create New Chat",
                onClick = { navController.navigate(Routes.CustomizeCat.route) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateRightPadding(LayoutDirection.Rtl)
                )
                .background(Color.White)
                .fillMaxSize()
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.chatbots.isEmpty() -> {
                    Text(
                        text = "Belum ada chatbot.\nTambahkan chatbot pertamamu!",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(uiState.chatbots) { chatbotWithHistory ->
                            ChatItem(
                                onClick = {
                                    navController.navigate(
                                        Routes.DetailChatbot.createRoute(chatbotWithHistory.chatbot.id)
                                    )
                                },
                                chatbotWithHistory = chatbotWithHistory
                            )
                            HorizontalDivider(
                                color = Color.LightGray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatbotScreenPreview() {
    MaterialTheme {
        ChatbotScreen(rememberNavController())
    }
}
