package com.hariku.feature_home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.R
import com.hariku.core.ui.components.Routes
import com.hariku.feature_home.presentation.components.ActivityCard
import com.hariku.feature_home.presentation.components.ChatCard
import com.hariku.feature_home.presentation.components.MoodCard
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.hariku.core.ui.theme.BgLightAlt5
import com.hariku.core.ui.theme.Coral90
import com.hariku.core.ui.theme.Neutral100
import com.hariku.core.ui.theme.Yellow30
import com.hariku.core.ui.theme.Blue20
import com.hariku.core.ui.theme.Blue40
import com.hariku.core.ui.theme.Blue70
import com.hariku.core.ui.theme.Green30
import com.hariku.core.ui.theme.Yellow40
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.LocalThemeState

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    moodViewModel: MoodViewModel = koinViewModel(),
    homeViewModel: HomeViewModel = koinViewModel()
) {
    val isDark = LocalThemeState.current.isDarkTheme

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AdaptiveColors.adaptiveBackground())
    ) {
        if (!isDark) {
            Image(
                painter = painterResource(id = R.drawable.home_bg_lightmode),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                AdaptiveColors.adaptiveCardBackground(), 
                                RoundedCornerShape(16.dp)
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_emote_biasa),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable(
                                    onClick = {
                                        navController.navigate(Routes.Profile.route)
                                    }
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Izora Talia",
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = AdaptiveColors.adaptiveText(),
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            navController.navigate(Routes.SosGraph.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Coral90),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("SOS", color = Neutral100, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(if (isDark) 24.dp else 150.dp))
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            AdaptiveColors.adaptiveBackground(),
                            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    MoodCard(viewModel = moodViewModel)
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Green30),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clickable{
                                    navController.navigate(Routes.CreateNotePrompt.route)
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_home_motivation),
                                    contentDescription = "Journal",
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .padding(
                                        top = 16.dp,
                                        bottom = 16.dp,
                                        start = 16.dp,
                                        end = 120.dp
                                    )
                            ) {
                                Text(
                                    text = "Mulai menulis jurnal harian dan temukan kekuatan dalam refleksi!",
                                    color = Neutral100,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                    // Chat Terakhir Section - Only show if there are chatbots
                    if (homeViewModel.chatbotUiState.chatbots.isNotEmpty()) {
                        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                            Text(
                                text = "Chat Terakhir",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = AdaptiveColors.adaptiveText(),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            // Display max 2 chatbots
                            homeViewModel.chatbotUiState.chatbots.take(2).forEachIndexed { index, chatbotWithHistory ->
                                ChatCard(
                                    title = chatbotWithHistory.chatbot.name,
                                    message = chatbotWithHistory.lastMessage.ifEmpty { "No messages yet" },
                                    date = formatTimestamp(chatbotWithHistory.lastMessageTime),
                                    unreadCount = chatbotWithHistory.unreadCount,
                                    avatarResId = if (chatbotWithHistory.chatbot.avatarResId != 0) chatbotWithHistory.chatbot.avatarResId else R.drawable.ic_launcher_foreground,
                                    backgroundColor = if (index == 0) Yellow30 else (if (isDark) AdaptiveColors.adaptiveCardBackground() else BgLightAlt5),
                                    onClick = {
                                        navController.navigate(Routes.DetailChatbot.createRoute(chatbotWithHistory.chatbot.id))
                                    }
                                )
                                if (index < 1) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                        Text(
                            text = "Panduan Aktivitas",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = AdaptiveColors.adaptiveText(),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 24.dp)
                    ) {
                        item {
                            ActivityCard(
                                "Panduan Meditasi",
                                R.drawable.img_home_meditation,
                                backgroundColor = Blue40,
                                onClick = {
                                    navController.navigate(Routes.Meditation.route)
                                }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            ActivityCard(
                                "Latihan 5 Panca Indra",
                                R.drawable.img_home_senses,
                                backgroundColor = Yellow40,
                                onClick = {
                                    navController.navigate(Routes.Senses.route)
                                }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            ActivityCard(
                                "Artikel Pilihan",
                                R.drawable.img_home_article,
                                backgroundColor = if (isDark) Blue20 else Blue70,
                                onClick = {
                                    navController.navigate(Routes.Article.route)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun formatTimestamp(timestamp: Long): String {
    if (timestamp == 0L) return "-"
    return try {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM", Locale.getDefault())
        format.format(date)
    } catch (e: Exception) {
        "-"
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}
