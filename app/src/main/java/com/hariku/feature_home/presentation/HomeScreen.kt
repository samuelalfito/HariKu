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

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    moodViewModel: MoodViewModel = koinViewModel()
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_bg_lightmode),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
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
                            .background(Color.White, RoundedCornerShape(16.dp))
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
                                        navController.navigate(Routes.PROFILE)
                                    }
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Izora Talia",
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A7A)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("SOS", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(150.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.White,
                            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    MoodCard(viewModel = moodViewModel)
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF71a77a)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        Box {
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
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                        Text(
                            text = "Chat Terakhir",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        ChatCard(
                            title = "HariKu",
                            message = "Halo! Aku Hariku, siap membantumu...",
                            date = "29/05",
                            unreadCount = 2
                        )
                    }
                    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                        Text(
                            text = "Panduan Aktivitas",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
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
                                backgroundColor = Color(0xFFa0cfe7)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            ActivityCard(
                                "Latihan 5 Panca Indra",
                                R.drawable.img_home_senses,
                                backgroundColor = Color(0xFFfff0e5)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            ActivityCard(
                                "Artikel Pilihan",
                                R.drawable.img_home_article,
                                backgroundColor = Color(0xFFcbe1fc)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}