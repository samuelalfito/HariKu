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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R

@Composable
fun CreateNotePromptDoneScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
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
            colors = CardDefaults.cardColors(containerColor = Color(0xFF82b3bb)),
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
                        .padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
                ) {
                    Text(
                        "Terima kasih telah membagi perasaanmu! ",
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
                        painter = painterResource(id = R.drawable.img_cat_head),
                        contentDescription = "AI Prompt Icon",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 12.dp)
                            .offset(x = 20.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.img_cat_hand),
                        contentDescription = "AI Prompt Icon",
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .offset(x = (-40).dp, y = 4.dp)
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
                    "Berdasarkan yang sedang kamu alami saat ini, ini beberapa topik jurnal yang bisa kamu oakai. Pilih salah satu prompt untuk journalmu!",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF222222)
                )
                Spacer(Modifier.height(12.dp))
                val prompts = listOf(
                    "Apa yang telah membantu kecemasanku di masa lalu?",
                    "Apa pemicu dari kecemasanku? Kapan pertama terjadi?",
                    "Kecemasan tidak menghambatku. Aku kuat karena...",
                    "Ingatanku yang paling bahagia",
                    "Apa yang membuatmu cemas?",
                    "Apakah kecemasanku realistis?"
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    prompts.forEach { prompt ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF6F6F6))
                                .padding(horizontal = 12.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                prompt,
                                fontSize = 15.sp,
                                color = Color(0xFF222222),
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_chevron_right),
                                contentDescription = "Next",
                                tint = Color(0xFFD88C5A),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {/* TODO */ },
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
            onClick = {/* TODO */ },
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
    CreateNotePromptDoneScreen()
}