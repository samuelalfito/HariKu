package com.hariku.feature_statistic.presentation.components

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.LocalThemeState

@Composable
fun GroupedBarChart(data: List<WeeklyData>) {
    val yAxisMax = 50f
    val yAxisSteps = 5
    val isDark = LocalThemeState.current.isDarkTheme
    
    val textColor = AdaptiveColors.adaptiveTextSecondary().toArgb()
    val labelColor = AdaptiveColors.adaptiveText().toArgb()
    val axisColor = AdaptiveColors.adaptiveDivider()
    
    val textPaint = remember(textColor) {
        Paint().apply {
            color = textColor
            textSize = 32f
            textAlign = Paint.Align.RIGHT
            typeface = Typeface.DEFAULT
        }
    }
    
    val labelPaint = remember(labelColor) {
        Paint().apply {
            color = labelColor
            textSize = 36f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT
        }
    }
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        val paddingBottom = 140f
        val paddingLeft = 80f
        val chartWidth = size.width - paddingLeft
        val chartHeight = size.height - paddingBottom
        
        val stepHeight = chartHeight / yAxisSteps
        
        for (i in 1..yAxisSteps) {
            val y = chartHeight - (i * stepHeight)
            val value = (i * (yAxisMax / yAxisSteps)).toInt()
            
            drawLine(
                color = ColorGridLine,
                start = Offset(paddingLeft, y),
                end = Offset(size.width, y),
                strokeWidth = 2f
            )
            
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    value.toString(),
                    paddingLeft - 15f,
                    y + 10f,
                    textPaint
                )
            }
        }
        
        drawLine(
            color = axisColor,
            start = Offset(paddingLeft, 0f),
            end = Offset(paddingLeft, chartHeight),
            strokeWidth = 2f
        )
        drawLine(
            color = axisColor,
            start = Offset(paddingLeft, chartHeight),
            end = Offset(size.width, chartHeight),
            strokeWidth = 2f
        )
        
        val groupWidth = chartWidth / data.size
        val barWidth = (groupWidth * 0.7f) / 3
        val spacing = (groupWidth * 0.3f) / 2
        
        data.forEachIndexed { index, item ->
            val startX = paddingLeft + (index * groupWidth) + spacing
            
            fun drawBar(value: Float, order: Int, color: Color) {
                val calculatedHeight = (value / yAxisMax) * chartHeight
                val barHeight = calculatedHeight.coerceIn(0f, chartHeight)
                val barLeft = startX + (order * barWidth)
                
                drawRect(
                    color = color,
                    topLeft = Offset(barLeft, chartHeight - barHeight),
                    size = Size(barWidth, barHeight)
                )
            }
            
            drawBar(item.negative, 0, BarColorNegative)
            drawBar(item.positive, 1, BarColorPositive)
            drawBar(item.neutral, 2, BarColorNeutral)
            
            val labelX = startX + (1.5f * barWidth)
            val labelY = chartHeight + 40f
            
            drawIntoCanvas {
                it.nativeCanvas.save()
                it.nativeCanvas.rotate(45f, labelX, labelY)
                it.nativeCanvas.drawText(
                    item.dateLabel,
                    labelX,
                    labelY,
                    labelPaint
                )
                it.nativeCanvas.restore()
            }
            
            drawLine(
                color = axisColor,
                start = Offset(labelX, chartHeight),
                end = Offset(labelX, chartHeight + 10f),
                strokeWidth = 3f
            )
        }
    }
}
