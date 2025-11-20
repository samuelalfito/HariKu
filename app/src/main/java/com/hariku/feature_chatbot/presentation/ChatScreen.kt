package com.hariku.feature_chatbot.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.R
import com.hariku.core.ui.components.FloatingActButton
import com.hariku.core.ui.components.Routes
import com.hariku.core.ui.components.SosTopBar
import com.hariku.feature_chatbot.domain.model.ChatbotData
import com.hariku.feature_chatbot.presentation.components.ChatItem

val chatbotData = listOf(
    ChatbotData(
        1,
        R.drawable.ic_avatar_hariku,
        "Hariku",
        "Halo! Aku Hariku, siap membantumu...",
        "29/05",
        2
    ),
    ChatbotData(
        2,
        R.drawable.ic_avatar_quesar,
        "Quesar",
        "Halo! Aku Hariku, siap membantumu...",
        "29/05",
        1
    ),
    ChatbotData(
        3,
        R.drawable.ic_avatar_hariku2,
        "Hariku (2)",
        "Halo! Aku Hariku, siap membantumu...",
        "29/05",
        0
    ),
    ChatbotData(
        4,
        R.drawable.ic_avatar_noir,
        "Noir",
        "Halo! Aku Hariku, siap membantumu...",
        "29/05",
        0
    ),
    ChatbotData(
        5,
        R.drawable.ic_avatar_maman,
        "Maman (Baru!)",
        "Halo! Aku Maman, siap membantumu...",
        "29/05",
        0
    ),
    ChatbotData(
        5,
        R.drawable.ic_avatar_maman,
        "Maman (Baru!)",
        "Halo! Aku Maman, siap membantumu...",
        "29/05",
        0
    ),
    ChatbotData(
        5,
        R.drawable.ic_avatar_maman,
        "Maman (Baru!)",
        "Halo! Aku Maman, siap membantumu...",
        "29/05",
        0
    ),
    ChatbotData(
        5,
        R.drawable.ic_avatar_maman,
        "Maman (Baru!)",
        "Halo! Aku Maman, siap membantumu...",
        "29/05",
        0
    ),
    ChatbotData(
        5,
        R.drawable.ic_avatar_maman,
        "Maman (Baru!)",
        "Halo! Aku Maman, siap membantumu...",
        "29/05",
        0
    ),
    ChatbotData(
        5,
        R.drawable.ic_avatar_maman,
        "Maman (Baru!)",
        "Halo! Aku Maman, siap membantumu...",
        "29/05",
        0
    ),
    ChatbotData(
        5,
        R.drawable.ic_avatar_maman,
        "Maman (Baru!)",
        "Halo! Aku Maman, siap membantumu...",
        "29/05",
        0
    ),
    ChatbotData(
        5,
        R.drawable.ic_avatar_maman,
        "Maman (Baru!)",
        "Halo! Aku Maman, siap membantumu...",
        "29/05",
        0
    ),
    ChatbotData(
        5,
        R.drawable.ic_avatar_maman,
        "Maman (Baru!)",
        "Halo! Aku Maman, siap membantumu...",
        "29/05",
        0
    ),
    ChatbotData(
        5,
        R.drawable.ic_avatar_maman,
        "Maman (Baru!)",
        "Halo! Aku Maman, siap membantumu...",
        "29/05",
        0
    ),
)

@Composable
fun ChatScreen(navController: NavController) {
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
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateRightPadding(LayoutDirection.Rtl)
                )
                .background(Color.White)
                .fillMaxSize()
        ) {
            items(chatbotData) { chatbot ->
                ChatItem(
                    onClick = {
                        navController.navigate(Routes.DetailChatbot.createRoute(chatbot.id.toString()))
                    },
                    chatbotData = chatbot
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

@Preview
@Composable
fun ChatScreenPreview() {
    MaterialTheme {
        ChatScreen(rememberNavController())
    }
}
