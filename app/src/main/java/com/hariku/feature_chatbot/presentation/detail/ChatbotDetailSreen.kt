package com.hariku.feature_chatbot.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.core.ui.components.Routes
import com.hariku.feature_chatbot.presentation.components.ChatDetailTopBar
import com.hariku.feature_chatbot.presentation.components.ChatTextFieldBar
import com.hariku.feature_chatbot.presentation.components.MessageBubble

enum class MessageAuthor {
    USER, BOT
}

data class ChatMessage(
    val id: Int,
    val text: String,
    val author: MessageAuthor,
)

val chatbotMessages = listOf(
    ChatMessage(
        1,
        "Halo Izora! Perkenalkan aku Hariku, chatbot yang siap menemanimu dalam mengatasi kecemasan",
        MessageAuthor.BOT
    ),
    ChatMessage(
        2,
        "Ceritakan keluh kesahmu! Aku akan meringkas percakapan harian kita menjadi jurnal. Bisa diakses di halaman riwayat",
        MessageAuthor.BOT
    ),
    ChatMessage(3, "Salam kenal!", MessageAuthor.BOT),
    ChatMessage(4, "Halo juga!", MessageAuthor.USER),
    ChatMessage(5, "Jadi, bagaimana perasaanmu hari ini, Izora?", MessageAuthor.BOT),
    ChatMessage(6, "Sedih", MessageAuthor.USER),
)

@Composable
fun ChatbotDetailScreen(navController: NavController, chatbotId: String) {
    var messageText by remember { mutableStateOf("") }
    
    Scaffold(
        containerColor = Color(0xFFFDE8D8),
        topBar = {
            ChatDetailTopBar(
                chatbotId = chatbotId,
                onBackClick = { navController.popBackStack() },
                onSosClick = { navController.navigate(Routes.SosGraph.route) })
        },
        bottomBar = {
            ChatTextFieldBar(
                text = messageText,
                onTextChanged = { messageText = it }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                DateSeparator(text = "SESSION STARTED - 31/05/23")
            }
            
            items(chatbotMessages) { message ->
                MessageBubble(
                    text = message.text,
                    isBot = message.author == MessageAuthor.BOT
                )
            }
        }
    }
}

@Composable
fun DateSeparator(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFFF3D9C9),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6F4E37)
            )
        }
    }
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
