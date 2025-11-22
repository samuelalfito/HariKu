package com.hariku.feature_chatbot.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.R


enum class MessageAuthor {
    USER, BOT
}

data class ChatMessage(
    val id: Int,
    val text: String,
    val author: MessageAuthor
)

val mockMessageList = listOf(
    ChatMessage(1, "Halo Izora! Perkenalkan aku Hariku, chatbot yang siap menemanimu dalam mengatasi kecemasan", MessageAuthor.BOT),
    ChatMessage(2, "Ceritakan keluh kesahmu! Aku akan meringkas percakapan harian kita menjadi jurnal. Bisa diakses di halaman riwayat", MessageAuthor.BOT),
    ChatMessage(3, "Salam kenal!", MessageAuthor.BOT),
    ChatMessage(4, "Halo juga!", MessageAuthor.USER),
    ChatMessage(5, "Jadi, bagaimana perasaanmu hari ini, Izora?", MessageAuthor.BOT),
    ChatMessage(6, "Sedih", MessageAuthor.USER),
)

@Composable
fun ChatDetailScreen(navController: NavController, chatbotId: String) {
    var messageText by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color(0xFFFDE8D8),
        topBar = { ChatDetailTopBar({navController.popBackStack()}) },
        bottomBar = {
            ChatDetailInputBar(
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            reverseLayout = false
        ) {
            item {
                DateSeparator(text = "SESSION STARTED - 31/05/23")
            }

            items(mockMessageList) { message ->
                when (message.author) {
                    MessageAuthor.BOT -> BotMessageBubble(text = message.text)
                    MessageAuthor.USER -> UserMessageBubble(text = message.text)
                }
            }
        }
    }
}

@Composable
fun ChatDetailTopBar(backTodo: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
            .padding(bottom = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                backTodo()
            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Kembali",
                    modifier = Modifier.size(50.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_avatar_hariku),
                contentDescription = "Avatar Hariku",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Hariku",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .width(72.76923.dp)
                    .height(39.69231.dp)
                    .background(
                        color = Color(0xFFF68674),
                        shape = RoundedCornerShape(size = 55.1282.dp)
                    )
                    .clickable { /* TODO: Aksi SOS */ },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SOS",
                    style = TextStyle(
                        fontSize = 16.54.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFDFCFC),
                        letterSpacing = 1.1.sp,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun BotMessageBubble(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .padding(12.dp)
        ) {
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun UserMessageBubble(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFFFFD4B6),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 0.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .padding(12.dp)
        ) {
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailInputBar(text: String, onTextChanged: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Kolom Teks
            TextField(
                value = text,
                onValueChange = onTextChanged,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ketik Pesan Anda") },
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color(0xFFFDE8D8),
                    unfocusedContainerColor = Color(0xFFFDE8D8)
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFD9A188), CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = "Kirim",
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 8.dp)
        ) {
        }
    }
}


@Preview(showBackground = true, widthDp = 430, heightDp = 932)
@Composable
fun ChatDetailScreenPreview() {
    MaterialTheme {
        ChatDetailScreen(rememberNavController(), "0")
    }
}
