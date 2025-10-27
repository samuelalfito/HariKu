package com.hariku.feature_auth.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextLogo(borderPreview: Boolean = false) {
    var modifier = Modifier
        .fillMaxWidth()
    if (borderPreview) {
        modifier = modifier.border(
            width = 4.dp,
            shape = RectangleShape,
            color = Color.Red
        )
    }
    Text(
        text = "HariKu",
        fontSize = 78.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth(),
        color = Color(0xFFC97D50)
    )
}