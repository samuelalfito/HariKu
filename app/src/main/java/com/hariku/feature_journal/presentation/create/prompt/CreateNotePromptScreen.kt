package com.hariku.feature_journal.presentation.create.prompt

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hariku.R
import com.hariku.core.ui.components.Routes

@Composable
fun CreateNotePromptScreen(navController: NavController) {
    val otherFeelings = remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(12.dp))
        // TopBar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO: Back */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Back"
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                "Buat Prompt AI",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f),
                color = Color(0xFF222222)
            )
        }
        Spacer(Modifier.height(8.dp))
        // Banner Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF433230)),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Row(
                Modifier.fillMaxSize()
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    Text(
                        "Cari Ide untuk Jurnalmu dengan Prompt AI",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                }
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_light_source_1),
                        contentDescription = "Arrow Right",
                        modifier = Modifier.fillMaxHeight()
                    )
                    Image(
                        painter = painterResource(id = R.drawable.img_cat_hand),
                        contentDescription = "AI Prompt Icon",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 12.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        // Feelings Card
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                Modifier.padding(20.dp)
            ) {
                Text(
                    "Bagaimana Perasaanmu?",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF222222)
                )
                Spacer(Modifier.height(12.dp))
                val feelings = listOf(
                    "Senang", "Sedih", "Cemas", "Marah",
                    "Takut", "Stress", "Lelah", "Kecewa",
                    "Termotivasi", "Ingin Fokus", "Bosan",
                    "Kesepian", "Damai", "Malu", "Muak"
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(feelings) { feeling ->
                        val isHighlighted = feeling == "Cemas" || feeling == "Lelah"
                        Box(
                            modifier = Modifier
                                .padding(vertical = 2.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (isHighlighted) Color(0xFFD88C5A)
                                    else Color(0xFFF9E6D1)
                                )
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                        ) {
                            Text(
                                feeling,
                                color = if (isHighlighted) Color.White else Color.Black,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        // TextField Card
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            TextField(
                value = otherFeelings.value,
                onValueChange = {
                    otherFeelings.value = it
                },
                placeholder = {
                    Text(
                        "Tulis di sini apabila ada perasaan lain yang ingin kamu tambahkan....",
                        color = Color(0xFFBDBDBD)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Transparent),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { navController.navigate(Routes.CreateNotePromptCompleted.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD88C5A))
        ) {
            Text("Lanjut", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick = { navController.navigate(Routes.CreateNotePromptCompleted.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(2.dp, Color(0xFFD88C5A)),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
        ) {
            Text(
                "Lewati",
                color = Color(0xFFD88C5A),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CreateNotePromptScreen(NavController(LocalContext.current))
}