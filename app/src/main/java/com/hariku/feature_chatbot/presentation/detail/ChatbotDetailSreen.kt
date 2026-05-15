package com.hariku.feature_chatbot.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.R
import com.hariku.core.ui.components.Routes
import com.hariku.feature_chatbot.presentation.components.ChatDetailTopBar
import com.hariku.feature_chatbot.presentation.components.ChatTextFieldBar
import com.hariku.feature_chatbot.presentation.components.MessageBubble
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.Orange20
import com.hariku.core.ui.theme.Orange60
import com.hariku.core.ui.theme.LocalThemeState

@Composable
fun ChatbotDetailScreen(
    navController: NavController,
    chatbotId: String,
    viewModel: ChatbotDetailViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState
    var messageText by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isDark = LocalThemeState.current.isDarkTheme
    
    LaunchedEffect(chatbotId) {
        viewModel.initialize(chatbotId)
    }
    
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }
    
    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(uiState.messages.size - 1)
            }
        }
    }
    
    Scaffold(
        containerColor = AdaptiveColors.adaptiveBackground(),
        topBar = {
            ChatDetailTopBar(
                chatbotId = uiState.chatbot?.name ?: chatbotId,
                avatarResId = uiState.chatbot?.avatarResId ?: R.drawable.ic_avatar_hariku,
                onBackClick = { navController.popBackStack() },
                onSosClick = { navController.navigate(Routes.SosGraph.route) }
            )
        },
        bottomBar = {
            ChatTextFieldBar(
                text = messageText,
                onTextChanged = { messageText = it },
                onSendClick = {
                    if (messageText.isNotBlank() && !uiState.isSending) {
                        viewModel.sendMessage(messageText)
                        messageText = ""
                    }
                },
                enabled = !uiState.isSending
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AdaptiveColors.adaptiveBackground())
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Orange60
                    )
                }
                uiState.messages.isEmpty() -> {
                    Text(
                        text = "Mulai percakapan dengan ${uiState.chatbot?.name ?: "bot"}!",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = AdaptiveColors.adaptiveTextSecondary()
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        val groupedMessages = uiState.messages.groupBy { message ->
                            getDateLabel(message.timestamp)
                        }
                        
                        groupedMessages.forEach { (dateLabel, messages) ->
                            item {
                                DateSeparator(text = dateLabel)
                            }
                            
                            items(messages) { message ->
                                MessageBubble(
                                    text = message.message,
                                    isBot = !message.isFromUser
                                )
                            }
                        }
                        
                        // Show loading indicator when sending
                        if (uiState.isSending) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.padding(16.dp),
                                        color = Orange60
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DateSeparator(text: String) {
    val isDark = LocalThemeState.current.isDarkTheme
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isDark) Color(0xFF222222) else Color(0xFFF3D9C9), // Using Neutral20 and BgLightAlt14 equivalents
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDark) Color(0xFFD88C5A) else Orange20 // Using Orange70 in dark mode
            )
        }
    }
}

private fun getDateLabel(timestamp: Long): String {
    val messageDate = Calendar.getInstance().apply {
        timeInMillis = timestamp
    }
    
    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -1)
    }
    
    return when {
        isSameDay(messageDate, today) -> "Hari Ini"
        isSameDay(messageDate, yesterday) -> "Kemarin"
        else -> {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            "SESSION STARTED - ${dateFormat.format(Date(timestamp))}"
        }
    }
}

private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

@Preview
@Composable
fun ChatbotDetailScreenPreview() {
    MaterialTheme {
        ChatbotDetailScreen(
            rememberNavController(), "1"
        )
    }
}
