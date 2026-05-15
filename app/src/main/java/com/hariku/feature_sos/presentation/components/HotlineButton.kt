package com.hariku.feature_sos.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.Rose35

@Composable
fun HotlineButton(
    title: String,
    prefix: String,
    hotline: String,
    onClick: () -> Unit,
) {
    val hotlineColor = Rose35
    val textHotlineColor = AdaptiveColors.adaptiveText()
    
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(3.dp, Rose35)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = textHotlineColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = AdaptiveColors.adaptiveText(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(prefix)
                    }
                    withStyle(
                        style = SpanStyle(
                            color = hotlineColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(hotline)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    HotlineButton(
        title = "Hotline Polisi",
        prefix = "Hubungi ",
        hotline = "110",
        onClick = {}
    )
}
