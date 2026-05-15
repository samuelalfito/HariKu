package com.hariku.feature_chatbot.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.LocalThemeState
import com.hariku.core.ui.theme.Rose90

@Composable
fun MessageBubble(text: String, isBot: Boolean) {
    val isDark = LocalThemeState.current.isDarkTheme
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isBot) Arrangement.Start else Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .then(
                    if (isBot) Modifier.fillMaxWidth(0.8f) else Modifier
                )
                .background(
                    color = if (isBot) {
                        if (isDark) Color(0xFF1E1E1E) else Color.White
                    } else {
                        if (isDark) Color(0xFF6B2B3F) else Rose90 // Using Rose10 for dark mode
                    },
                    shape = RoundedCornerShape(
                        topStart = if (isBot) 0.dp else 16.dp,
                        topEnd = if (isBot) 16.dp else 0.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .padding(12.dp)
        ) {
            Text(
                text = text, 
                style = MaterialTheme.typography.bodyLarge,
                color = if (isBot) AdaptiveColors.adaptiveText() else Color.White
            )
        }
    }
}
