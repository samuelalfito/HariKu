package com.hariku.feature_journal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.hariku.feature_journal.domain.model.TextElement
import kotlin.math.roundToInt

@Composable
fun EditableText(
    textElement: TextElement,
    isSelected: Boolean,
    onDrag: (Offset) -> Unit = {},
    onClick: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    onDelete: () -> Unit = {},
    onScaleChange: (Float) -> Unit = {},
) {
//    var offsetX by remember { mutableFloatStateOf(textElement.offsetX) }
//    var offsetY by remember { mutableFloatStateOf(textElement.offsetY) }
//    var scale by remember { mutableFloatStateOf(textElement.scale) }
    
//    LaunchedEffect(textElement.offsetX, textElement.offsetY, textElement.scale) {
//        offsetX = textElement.offsetX
//        offsetY = textElement.offsetY
//        scale = textElement.scale
//    }
    
//    val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
//        if (isSelected) {
//            scale = (scale * zoomChange).coerceIn(0.5f, 3f)
//            offsetX += offsetChange.x
//            offsetY += offsetChange.y
//            onDrag(Offset(offsetX, offsetY))
//            onScaleChange(scale)
//        }
//    }

    val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
        if (isSelected) {
            // Hitung skala baru dan kirim ke ViewModel
            val newScale = (textElement.scale * zoomChange).coerceIn(0.5f, 3f)
            onScaleChange(newScale)

            // Hitung posisi baru dan kirim ke ViewModel
            val newOffsetX = textElement.offsetX + offsetChange.x
            val newOffsetY = textElement.offsetY + offsetChange.y
            onDrag(Offset(newOffsetX, newOffsetY))
        }
    }
    
    Box(
        modifier = Modifier
//            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .offset { IntOffset(textElement.offsetX.roundToInt(), textElement.offsetY.roundToInt()) }
            .zIndex(if (isSelected) 10f else 1f)) {
        // Delete button when selected
        if (isSelected) {
            // Text input field
            BasicTextField(
                value = textElement.text,
                onValueChange = onTextChange,
                modifier = Modifier
//                    .graphicsLayer(
//                        scaleX = scale, scaleY = scale
//                    )
                    .graphicsLayer(
                        scaleX = textElement.scale,
                        scaleY = textElement.scale
                    )
                    .then(
                        Modifier.transformable(state = transformState)
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { onClick() })
                    }
                    .then(
                        Modifier.border(
                            2.dp, Color(0xFFCF6D49), RoundedCornerShape(4.dp)
                        )
                    )
                    .padding(8.dp)
                    .widthIn(min = 50.dp, max = 300.dp),
                textStyle = TextStyle(
                    fontSize = textElement.fontSize.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = when (textElement.fontFamily) {
                        "Serif" -> FontFamily.Serif
                        "Sans Serif" -> FontFamily.SansSerif
                        "Monospace" -> FontFamily.Monospace
                        "Cursive" -> FontFamily.Cursive
                        else -> FontFamily.Default
                    },
                    color = textElement.color,
                    textAlign = textElement.textAlign,
                    textDecoration = if (textElement.isUnderlined) TextDecoration.Underline
                    else TextDecoration.None
                ),
                cursorBrush = SolidColor(Color.White))
            
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopStart)
                    .offset((-8).dp, (-8).dp)
                    .background(Color(0xFFCF6D49), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        } else {
            Text(
                text = textElement.text,
                fontSize = textElement.fontSize.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = when (textElement.fontFamily) {
                    "Serif" -> FontFamily.Serif
                    "Sans Serif" -> FontFamily.SansSerif
                    "Monospace" -> FontFamily.Monospace
                    "Cursive" -> FontFamily.Cursive
                    else -> FontFamily.Default
                },
                color = textElement.color,
                textAlign = textElement.textAlign,
                textDecoration = if (textElement.isUnderlined) TextDecoration.Underline
                else TextDecoration.None,
                modifier = Modifier
//                    .graphicsLayer(
//                        scaleX = scale, scaleY = scale
//                    )
                    .graphicsLayer(
                        scaleX = textElement.scale,
                        scaleY = textElement.scale
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { onClick() })
                    })
        }
    }
}