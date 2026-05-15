package com.hariku.feature_chatbot.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hariku.R
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.Coral70
import com.hariku.core.ui.theme.LocalThemeState

@Composable
fun ChatTextFieldBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onSendClick: () -> Unit = {},
    enabled: Boolean = true
) {
    val isDark = LocalThemeState.current.isDarkTheme
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                AdaptiveColors.adaptiveCardBackground(), 
                RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = onTextChanged,
                modifier = Modifier.weight(1f),
                placeholder = { 
                    Text(
                        "Ketik Pesan Anda", 
                        color = AdaptiveColors.adaptiveTextSecondary()
                    ) 
                },
                shape = RoundedCornerShape(24.dp),
                enabled = enabled,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = if (isDark) Color(0xFF222222) else Color(0xFFFDE8D8), // Neutral20 / BgLightAlt6
                    unfocusedContainerColor = if (isDark) Color(0xFF222222) else Color(0xFFFDE8D8),
                    focusedTextColor = AdaptiveColors.adaptiveText(),
                    unfocusedTextColor = AdaptiveColors.adaptiveText()
                )
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            IconButton(
                onClick = onSendClick,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (enabled && text.isNotBlank()) Coral70 else AdaptiveColors.adaptiveDisabled(),
                        shape = CircleShape
                    ),
                enabled = enabled && text.isNotBlank()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = "Kirim",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 8.dp)
        ) {
        }
    }
}
