package com.hariku.feature_pin.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.core.ui.theme.AdaptiveColors

@Composable
fun PinDotsComposable(count: Int, filled: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(34.dp),
    ) {
        repeat(count) { index ->
            val color = if (index < filled) AdaptiveColors.adaptiveText() else AdaptiveColors.adaptiveDivider()
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .background(color, CircleShape)
            )
        }
    }
}

@Composable
fun NumpadComposable(
    onNumberClick: (Int) -> Unit,
    onBackspaceClick: () -> Unit,
) {
    val buttonSize = 72.dp
    val spacing = 24.dp
    
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumberButton(number = "1", size = buttonSize, onClick = { onNumberClick(1) })
            NumberButton(number = "2", size = buttonSize, onClick = { onNumberClick(2) })
            NumberButton(number = "3", size = buttonSize, onClick = { onNumberClick(3) })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumberButton(number = "4", size = buttonSize, onClick = { onNumberClick(4) })
            NumberButton(number = "5", size = buttonSize, onClick = { onNumberClick(5) })
            NumberButton(number = "6", size = buttonSize, onClick = { onNumberClick(6) })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            NumberButton(number = "7", size = buttonSize, onClick = { onNumberClick(7) })
            NumberButton(number = "8", size = buttonSize, onClick = { onNumberClick(8) })
            NumberButton(number = "9", size = buttonSize, onClick = { onNumberClick(9) })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            Spacer(modifier = Modifier.size(buttonSize))
            NumberButton(number = "0", size = buttonSize, onClick = { onNumberClick(0) })
            
            Box(
                modifier = Modifier
                    .size(buttonSize)
                    .background(AdaptiveColors.adaptiveCardBackground(), CircleShape)
                    .clip(CircleShape)
                    .clickable { onBackspaceClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backspace),
                    contentDescription = "Backspace",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun NumberButton(
    number: String,
    size: Dp,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(AdaptiveColors.adaptiveCardBackground(), CircleShape)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number,
            style = TextStyle(
                fontSize = 38.sp,
                fontWeight = FontWeight(500),
                color = AdaptiveColors.adaptiveText(),
            )
        )
    }
}
