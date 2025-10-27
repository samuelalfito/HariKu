package com.hariku.feature_onboarding.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import com.hariku.R

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFCFC))
    ) {
        Text(
            text = "HariKu",
            style = TextStyle(
                color = Color(0xFFC97D50),
                fontSize = 78.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-100).dp)
        )

        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Splash Cat",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}