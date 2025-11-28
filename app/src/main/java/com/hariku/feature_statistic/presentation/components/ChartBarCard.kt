package com.hariku.feature_statistic.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.feature_statistic.domain.model.WeeklySentiment

val BarColorNegative = Color(0xFFF0A096)
val BarColorPositive = Color(0xFFAEE3EB)
val BarColorNeutral = Color(0xFFEBCBA6)
val ColorTextDark = Color(0xFF2D2D3A)
val ColorGridLine = Color(0xFFE0E0E0)

data class WeeklyData(
    val dateLabel: String,
    val negative: Float,
    val positive: Float,
    val neutral: Float
)

@Composable
fun ChartBarCard(
    weeklySentiments: List<WeeklySentiment> = emptyList()
) {
    var expanded by remember { mutableStateOf(true) }
    
    val chartData = weeklySentiments.map {
        WeeklyData(
            dateLabel = it.weekLabel,
            negative = it.negative,
            positive = it.positive,
            neutral = it.neutral
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sentimen Jurnal Mingguan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = ColorTextDark
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Toggle",
                    tint = Color.Gray
                )
            }
            
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp),
                thickness = 1.dp,
                color = Color.LightGray.copy(alpha = 0.5f)
            )
            
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(animationSpec = tween(300)),
                exit = fadeOut() + shrinkVertically(animationSpec = tween(300))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    
                    if (chartData.isEmpty()) {
                        Text(
                            text = "Belum ada data sentimen mingguan",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ) {
                            GroupedBarChart(data = chartData)
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            LegendItem(BarColorNegative, "Negatif")
                            LegendItem(BarColorPositive, "Positif")
                            LegendItem(BarColorNeutral, "Netral")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ChartBarCardPreview() {
    ChartBarCard()
}