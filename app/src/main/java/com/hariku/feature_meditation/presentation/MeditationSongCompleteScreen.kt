package com.hariku.feature_meditation.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R

@Composable
fun MeditationSongCompletedScreen() {
    val PrimaryColor = Color(0xFFD17F4B)
    val TextDarkColor = Color(0xFF333333)
    val BlackColor = Color(0xFF000000)
    val ButtonCornerRadius = 16.dp

    val onReturnToMeditationClick: () -> Unit = { }
    val onRepeatClick: () -> Unit = { }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3D3D3))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Color(0xFFFDFCFC))
                .padding(top = 22.dp, bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mengapa Cemas",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkColor
            )
            Text(
                text = "Meditasi",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 32.dp, bottom = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.selesai),
                        contentDescription = "Gambar Kucing Selesai",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(240.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Selesai!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = BlackColor,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Satu langkah lebih dekat dalam mengatasi kecemasan",
                        fontSize = 18.sp,
                        color = TextDarkColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = onReturnToMeditationClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        shape = RoundedCornerShape(ButtonCornerRadius),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
                    ) {
                        Text(
                            text = "Kembali ke Halaman Meditasi",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = onRepeatClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        shape = RoundedCornerShape(ButtonCornerRadius),
                        border = BorderStroke(2.dp, PrimaryColor),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = PrimaryColor,
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Ulangi Kembali",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}