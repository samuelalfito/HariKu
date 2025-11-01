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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R // Pastikan package R Anda sudah benar

/**
 * Layar Bantuan Profesional (Hotline).
 */
@Composable
fun SosProfessionalScreen() {
    Scaffold(
        containerColor = Color(0xFFFFFFFF), // Latar belakang yang sama
        topBar = { SosProTopBar() },
        bottomBar = { SosProHomeIndicator() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp), // Padding kiri & kanan
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(130.dp))

            Text(
                text = "Memiliki bantuan yang tepat pada waktu yang tepat dapat membantumu kembali lebih kuat.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Jika kamu memerlukan bantuan atau seseorang untuk diajak bicara, berikut adalah daftar hotline",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Daftar Tombol Hotline
            HotlineButton(
                title = "Hotline Pemerintah",
                prefix = "Hotline 24 jam: ",
                hotline = "119"
            )

            Spacer(modifier = Modifier.height(16.dp))

            HotlineButton(
                title = "Kementrian Kesehatan",
                prefix = "Hotline: ",
                hotline = "500-454"
            )

            Spacer(modifier = Modifier.height(16.dp))

            HotlineButton(
                title = "Save Yourselves Indonesia (Jakarta)",
                prefix = "Hotline: ",
                hotline = "082124326459"
            )
        }
    }
}

/**
 * Composable kustom untuk satu tombol hotline.
 */
@Composable
fun HotlineButton(
    title: String,
    prefix: String,
    hotline: String
) {
    val hotlineColor = Color(0xFFB55D6C) // Warna merah tua
    val textHotlineColor = Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = hotlineColor,
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.Transparent)
            .clickable { /* TODO: Aksi Panggil (Intent) */ }
            .padding(vertical = 16.dp), // Padding di dalam Box
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                color = textHotlineColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Teks hotline dengan gaya berbeda
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
                        append(prefix)
                    }
                    withStyle(style = SpanStyle(
                        color = hotlineColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    )) {
                        append(hotline)
                    }
                }
            )
        }
    }
}

/**
 * Top Bar untuk layar ini (Status Bar + Tombol Kembali).
 * Drawable: ic_signal, ic_wifi, ic_battery, ic_back_arrow
 */
@Composable
fun SosProTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent) // Latar belakang transparan
    ) {
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
fun SosProHomeIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
    }
}

// --- Preview ---

@Preview(showBackground = true, widthDp = 430, heightDp = 932)
@Composable
fun SosProfessionalScreenPreview() {
    MaterialTheme {
        SosProfessionalScreen()
    }
}
