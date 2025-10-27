package com.hariku.feature_auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TextLogo() {
    Text(
        text = "HariKu",
        fontSize = 78.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
    )
}