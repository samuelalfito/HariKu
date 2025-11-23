package com.hariku.feature_statistic.presentation.components

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hariku.feature_statistic.domain.model.ChartData
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ChartPie(
    modifier: Modifier = Modifier,
    data: List<ChartData>,
    strokeWidth: Dp = 60.dp
) {
    val textPaint = remember {
        Paint().apply {
            color = Color(0xFF4A4A6A).toArgb()
            textAlign = Paint.Align.CENTER
            textSize = 42f
            typeface = Typeface.DEFAULT_BOLD
        }
    }
    
    Canvas(modifier = modifier) {
        val totalValue = data.sumOf { it.value.toDouble() }.toFloat()
        var currentStartAngle = -90f
        
        val chartSize = size.minDimension
        val radius = (chartSize - strokeWidth.toPx()) / 2
        
        val centerOffset = Offset(size.width / 2, size.height / 2)
        
        data.forEach { item ->
            val sweepAngle = (item.value / totalValue) * 360f
            
            drawArc(
                color = item.color,
                startAngle = currentStartAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(
                    (size.width - chartSize) / 2 + strokeWidth.toPx() / 2,
                    (size.height - chartSize) / 2 + strokeWidth.toPx() / 2
                ),
                size = Size(chartSize - strokeWidth.toPx(), chartSize - strokeWidth.toPx()),
                style = Stroke(width = strokeWidth.toPx())
            )
            
            val midAngle = currentStartAngle + (sweepAngle / 2)
            val rad = Math.toRadians(midAngle.toDouble())
            
            val x = centerOffset.x + (radius * cos(rad)).toFloat()
            val y = centerOffset.y + (radius * sin(rad)).toFloat()
            
            val yAdjusted = y + (textPaint.textSize / 3)
            
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    item.displayPercentage,
                    x,
                    yAdjusted,
                    textPaint
                )
            }
            currentStartAngle += sweepAngle
        }
    }
}