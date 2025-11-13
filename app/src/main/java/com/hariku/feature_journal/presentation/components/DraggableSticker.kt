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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun DraggableSticker(
    stickerElement: StickerElement,
    isSelected: Boolean,
    onDrag: (Offset) -> Unit,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onScaleChange: (Float) -> Unit,
    onRotationChange: (Float) -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(stickerElement.offsetX) }
    var offsetY by remember { mutableFloatStateOf(stickerElement.offsetY) }
    var scale by remember { mutableFloatStateOf(stickerElement.scale) }
    var rotation by remember { mutableFloatStateOf(stickerElement.rotation) }
    
    LaunchedEffect(stickerElement.offsetX, stickerElement.offsetY, stickerElement.scale, stickerElement.rotation) {
        offsetX = stickerElement.offsetX
        offsetY = stickerElement.offsetY
        scale = stickerElement.scale
        rotation = stickerElement.rotation
    }
    
    val transformState = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        if (isSelected) {
            scale = (scale * zoomChange).coerceIn(0.5f, 3f)
            rotation += rotationChange
            offsetX += offsetChange.x
            offsetY += offsetChange.y
            onDrag(Offset(offsetX, offsetY))
            onScaleChange(scale)
            onRotationChange(rotation)
        }
    }
    
    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .size(80.dp)
            .zIndex(if (isSelected) 10f else 1f)
    ) {
        // Sticker content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation
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
                        Color(0xFFCF6D49),
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
                    .background(Color(0xFFCF6D49), CircleShape)
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
