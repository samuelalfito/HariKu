package com.hariku.feature_journal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.hariku.feature_journal.domain.model.StickerElement
import kotlin.math.roundToInt
import com.hariku.core.ui.theme.Coral40

@Composable
fun DraggableSticker(
    stickerElement: StickerElement,
    isSelected: Boolean,
    onDrag: (Offset) -> Unit = {},
    onClick: () -> Unit = {},
    onDelete: () -> Unit = {},
    onScaleChange: (Float) -> Unit = {},
    onRotationChange: (Float) -> Unit = {}
) {
    val transformState = rememberTransformableState { zoomChange, offsetChange, rotChange ->
        if (isSelected) {
            val newScale = (stickerElement.scale * zoomChange).coerceIn(0.5f, 3f)
            val newRotation = stickerElement.rotation + rotChange
            val newOffsetX = stickerElement.offsetX + offsetChange.x
            val newOffsetY = stickerElement.offsetY + offsetChange.y
            
            onDrag(Offset(newOffsetX, newOffsetY))
            onScaleChange(newScale)
            onRotationChange(newRotation)
        }
    }
    
    Box(
        modifier = Modifier
            .offset { IntOffset(stickerElement.offsetX.roundToInt(), stickerElement.offsetY.roundToInt()) }
            .size(80.dp)
            .zIndex(if (isSelected) 10f else 1f)
    ) {
        // Sticker content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = stickerElement.scale,
                    scaleY = stickerElement.scale,
                    rotationZ = stickerElement.rotation
                )
                .then(
                    if (isSelected) {
                        Modifier.transformable(state = transformState)
                    } else {
                        Modifier
                    }
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onClick() }
                    )
                }
                .then(
                    if (isSelected) Modifier.border(
                        2.dp,
                        Coral40,
                        RoundedCornerShape(8.dp)
                    ) else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stickerElement.emoji,
                fontSize = 48.sp
            )
        }
        
        // Delete button
        if (isSelected) {
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopStart)
                    .offset((-8).dp, (-8).dp)
                    .background(Coral40, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
