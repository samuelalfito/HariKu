package com.hariku.feature_chatbot.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.LocalThemeState
import kotlin.math.roundToInt
import com.hariku.core.ui.theme.Coral20
import com.hariku.core.ui.theme.Orange80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreePointSlider(
    selectedPoint: Float,
    onPointSelected: (Float) -> Unit,
    startLabel: String,
    endLabel: String
) {
    var totalWidthPx by remember { mutableStateOf(0f) }
    val density = LocalDensity.current
    val thumbSize = 24.dp
    val thumbPx = with(density) { thumbSize.toPx() }
    val trackStartPx = thumbPx / 2f
    val isDark = LocalThemeState.current.isDarkTheme

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { totalWidthPx = it.width.toFloat() }
    ) {
        val trackEndPx = if (totalWidthPx > 0f) totalWidthPx - thumbPx / 2f else 0f
        val trackWidthPx = (trackEndPx - trackStartPx).coerceAtLeast(1f)

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .pointerInput(totalWidthPx) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                val x = offset.x.coerceIn(trackStartPx, trackEndPx)
                                val percent = ((x - trackStartPx) / trackWidthPx).coerceIn(0f, 1f)
                                onPointSelected(percent)
                            },
                            onDrag = { change, _ ->
                                val x = change.position.x.coerceIn(trackStartPx, trackEndPx)
                                val percent = ((x - trackStartPx) / trackWidthPx).coerceIn(0f, 1f)
                                onPointSelected(percent)
                                change.consume()
                            }
                        )
                    }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = thumbSize / 2, end = thumbSize / 2)
                        .height(24.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isDark) Color(0xFF222222) else Color(0xFFEFD0B8)) // Neutral20 / BgLightAlt9
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                        .clip(CircleShape)
                        .background(AdaptiveColors.adaptiveBackground())
                        .border(width = 2.dp, color = Orange80, shape = CircleShape)
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterEnd)
                        .clip(CircleShape)
                        .background(AdaptiveColors.adaptiveBackground())
                        .border(width = 2.dp, color = Orange80, shape = CircleShape)
                )

                val thumbCenterPx = trackStartPx + (selectedPoint.coerceIn(0f, 1f) * trackWidthPx)
                val thumbOffset = (thumbCenterPx - thumbPx / 2f).roundToInt()

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset { IntOffset(thumbOffset, 0) }
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(AdaptiveColors.adaptiveBackground())
                        .border(width = 3.dp, color = Coral20, shape = CircleShape)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .align(Alignment.Center)
                            .clip(CircleShape)
                            .background(Coral20)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .align(Alignment.Center)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = startLabel,
                    fontSize = 16.sp,
                    color = AdaptiveColors.adaptiveText(),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = endLabel,
                    fontSize = 16.sp,
                    color = AdaptiveColors.adaptiveText(),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun FeedbackTypeOption(
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        RadioButton(
            modifier = Modifier.size(24.dp),
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Coral20,
                unselectedColor = AdaptiveColors.adaptiveDivider()
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = AdaptiveColors.adaptiveText()
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = AdaptiveColors.adaptiveTextSecondary(),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun GoalCheckbox(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Coral20,
                uncheckedColor = AdaptiveColors.adaptiveDivider(),
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = AdaptiveColors.adaptiveText()
        )
    }
}
