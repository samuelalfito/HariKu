package com.hariku.feature_home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.core.ui.theme.Coral90
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.Orange50
import com.hariku.core.ui.theme.Yellow30
import com.hariku.core.ui.theme.LocalThemeState

@Composable
fun ChatCard(
    title: String,
    message: String,
    date: String,
    unreadCount: Int,
    avatarResId: Int = R.drawable.ic_launcher_foreground,
    backgroundColor: Color = Yellow30,
    onClick: () -> Unit
) {
    val isDark = LocalThemeState.current.isDarkTheme
    
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().clickable{ onClick() },
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = AdaptiveColors.adaptiveCardBackground())
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = avatarResId),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = AdaptiveColors.adaptiveText()
                )
                Text(
                    text = message,
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    color = if (isDark) Color(0xFFD88C5A) else Orange50 // Using Orange70 in dark mode
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = date,
                    fontSize = 12.sp,
                    color = AdaptiveColors.adaptiveTextSecondary()
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Only show badge if unreadCount > 0
                if (unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Coral90),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = unreadCount.toString(),
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
