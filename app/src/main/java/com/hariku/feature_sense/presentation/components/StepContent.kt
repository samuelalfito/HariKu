package com.hariku.feature_sense.presentation.components

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.core.ui.theme.Coral30
import com.hariku.feature_sense.domain.model.SenseStep
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.SpecialLightBrown

@Composable
fun StepContent(
    step: SenseStep,
    progress: Float,
    progressText: String,
    context: Context
) {
    val iconResId = context.resources.getIdentifier(
        step.iconResName,
        "drawable",
        context.packageName
    ).takeIf { it != 0 } ?: R.drawable.see
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(240.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = SpecialLightBrown,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(
                        width = 16.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
            
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = Coral30,
                    startAngle = -90f,
                    sweepAngle = 360 * progress,
                    useCenter = false,
                    style = Stroke(
                        width = 16.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
            
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "${step.title} Icon",
                modifier = Modifier.size(120.dp)
            )
            
            Text(
                text = progressText,
                fontSize = 16.sp,
                color = AdaptiveColors.adaptiveTextSecondary(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = step.title,
            color = AdaptiveColors.adaptiveText(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = step.description,
            color = AdaptiveColors.adaptiveTextSecondary(),
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            lineHeight = 22.sp
        )
    }
}
