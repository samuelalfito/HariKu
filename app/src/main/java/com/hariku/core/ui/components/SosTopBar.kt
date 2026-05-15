package com.hariku.core.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.core.ui.theme.Coral80
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.Neutral100
import com.hariku.core.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SosTopBar(title: String, onSosClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = AdaptiveColors.adaptiveText()
            )
        },
        actions = {
            Button(
                onClick = { onSosClick() },
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Coral80,
                    contentColor = Color.White
                ),
                shape = CircleShape
            ) {
                Text(
                    text = "SOS",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600)
                    )
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AdaptiveColors.adaptiveBackground(),
            titleContentColor = AdaptiveColors.adaptiveText(),
            actionIconContentColor = AdaptiveColors.adaptiveText()
        ),
        windowInsets = WindowInsets(0.dp)
    )
}
