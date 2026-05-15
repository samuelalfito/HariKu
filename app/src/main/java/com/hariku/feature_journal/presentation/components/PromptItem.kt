package com.hariku.feature_journal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.LocalThemeState
import com.hariku.core.ui.theme.Orange70

@Composable
fun PromptItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val isDark = LocalThemeState.current.isDarkTheme
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) {
                    if (isDark) Color(0xFF6F4E37) else Color(0xFFFFF3E0) // Orange20 or SpecialPeach
                } else {
                    AdaptiveColors.adaptiveDivider()
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text,
            fontSize = 15.sp,
            color = AdaptiveColors.adaptiveText(),
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = "Next",
            tint = if (isSelected) Orange70 else AdaptiveColors.adaptiveTextSecondary(),
            modifier = Modifier.size(20.dp)
        )
    }
}
