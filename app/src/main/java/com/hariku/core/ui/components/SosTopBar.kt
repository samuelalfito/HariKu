package com.hariku.core.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SosTopBar(title: String, onSosClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            Button(
                onClick = { onSosClick() },
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF68674)
                ),
                shape = RoundedCornerShape(size = 25.dp)
            ) {
                Text(
                    text = "SOS",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFDFCFC)
                    )
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        windowInsets = WindowInsets(0),
        expandedHeight = 80.dp
    )
}