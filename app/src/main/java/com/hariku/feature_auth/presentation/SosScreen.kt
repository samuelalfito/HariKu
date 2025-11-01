package com.hariku.feature_auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R // Pastikan package R Anda sudah benar

/**
 * Layar SOS untuk keadaan darurat.
 */
@Composable
fun SosScreen() {
    Scaffold(
        // Menggunakan warna latar belakang yang sama dengan layar PIN
        containerColor = Color(0xFFFFFFFF),
        topBar = { SosTopBar() },
        bottomBar = { HomeIndicator() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp), // Padding kiri & kanan untuk konten
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(180.dp))

            Text(
                text = "Apakah kamu dalam keadaan darurat?",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Kamu tidak sendiri.",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF242424)
            )

            Text(
                text = "Dapatkan bantuan hanya dalam satu panggilan.",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF242424)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Meminta bantuan bukan berarti kamu lemah.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Tombol 1: Teknik Panca Indra (Outlined)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFB55D6C), // Warna merah tua
                        shape = RoundedCornerShape(28.dp)
                    )
                    .background(Color.Transparent) // Latar belakang transparan
                    .clickable { /* TODO: Aksi Tombol 1 */ },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Coba Teknik 5 Panca Indra",
                    color = Color(0xFFB55D6C), // Warna teks sama dengan border
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol 2: Bantuan Profesional (Solid)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xFFB55D6C)) // Latar belakang merah tua
                    .clickable { /* TODO: Aksi Tombol 2 */ },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bantuan Profesional",
                    color = Color.White, // Warna teks putih
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

/**
 * Top Bar sederhana untuk layar SOS (Status Bar + Tombol Kembali).
 * Drawable yang dibutuhkan: ic_signal, ic_wifi, ic_battery, ic_back_arrow
 */
@Composable
fun SosTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            // Latar belakang transparan agar menyatu dengan scaffold
            .background(Color.Transparent)
    ) {

        Spacer(modifier = Modifier.height(10.dp))
        // --- 2. Tombol Kembali ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp), // Padding kecil untuk IconButton
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { /* TODO: Aksi Kembali */ }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Kembali",
                    modifier = Modifier.size(70.dp)
                )
            }
        }
    }
}

/**
 * Indikator Home (garis hitam di bawah).
 */
@Composable
fun HomeIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 8.dp),
        contentAlignment = Alignment.Center
    ){}
}

// --- Preview ---

@Preview(showBackground = true, widthDp = 430, heightDp = 932)
@Composable
fun SosScreenPreview() {
    MaterialTheme {
        SosScreen()
    }
}
