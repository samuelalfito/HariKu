package com.hariku.feature_journal.presentation.create.note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R

@Composable
fun CreateNoteScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .padding(horizontal = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(12.dp))
        // TopBar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO: Back */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Back",
                    tint = Color(0xFFD88C5A)
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                "Buat Jurnal",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFF222222),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(8.dp))
        // Card Ilustrasi
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                // Background ilustrasi
                Image(
                    painter = painterResource(id = R.drawable.ic_cat_handing_book),
                    contentDescription = "Cat Journal",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .size(90.dp)
                        .offset(x = 8.dp, y = 8.dp)
                )
                Column(
                    Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 110.dp, top = 32.dp)
                ) {
                    Text(
                        "Izora’s Journal",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF222222)
                    )
                    Spacer(Modifier.height(8.dp))
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Text(
                            "28 Feb 2022, 20:30 WIB",
                            fontSize = 13.sp,
                            color = Color(0xFF222222),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
                // Stars (dummy, replace with actual asset if available)
                Icon(
                    painter = painterResource(id = R.drawable.ic_cat_handing_book),
                    contentDescription = "Star",
                    tint = Color(0xFFD88C5A),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                        .offset(x = (-12).dp, y = 12.dp)
                )
            }
        }
        Spacer(Modifier.height(18.dp))
        // Card Form
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                Modifier.padding(20.dp)
            ) {
                // Judul Journal
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Judul Journal", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color(0xFF222222), modifier = Modifier.weight(1f))
                    Text("49/60", fontSize = 13.sp, color = Color(0xFFB1B1B1))
                }
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Isi judul Journalmu", color = Color(0xFFB1B1B1)) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                // Tambah Gambar
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Tambah Gambar", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color(0xFF222222), modifier = Modifier.weight(1f))
                    Text("1/3", fontSize = 13.sp, color = Color(0xFFB1B1B1))
                }
                Spacer(Modifier.height(8.dp))
                Divider(color = Color(0xFFE5E5E5), thickness = 1.dp)
                Spacer(Modifier.height(12.dp))
                Box(
                    Modifier
                        .size(90.dp, 110.dp)
                        .border(1.dp, Color(0xFFB1B1B1), RoundedCornerShape(12.dp))
                        .background(Color.White)
                ) {
                    // Dashed border not natively supported, use solid for now
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Add Image",
                        tint = Color(0xFFB1B1B1),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(Modifier.height(16.dp))
                // Teks
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Teks", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color(0xFF222222), modifier = Modifier.weight(1f))
                    Text("0/50000", fontSize = 13.sp, color = Color(0xFFB1B1B1))
                }
                Spacer(Modifier.height(8.dp))
                Divider(color = Color(0xFFE5E5E5), thickness = 1.dp)
                Spacer(Modifier.height(12.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Isi Jurnalmu di sini", color = Color(0xFFB1B1B1)) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(80.dp)
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD88C5A))
                ) {
                    Text("Selesai", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CreateNoteScreen()
}