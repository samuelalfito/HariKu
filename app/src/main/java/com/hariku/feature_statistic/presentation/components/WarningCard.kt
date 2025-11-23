package com.hariku.feature_statistic.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R

@Composable
fun WarningCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CardDefaults.shape),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CardDefaults.shape)
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_statistic_warning),
                contentDescription = "Warning Card Logo",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .absoluteOffset(x = -128.dp, y = -128.dp),
                alpha = 0.7f
            )
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PERINGATAN!",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(vertical = 28.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        append("Kami mendeteksi adanya beberapa kalimat yang " +
                                "mengindikasikan potensi adanya perasaan ingin ")

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xffB82B55),
                                fontWeight = FontWeight.Bold
                            )
                        ){
                            append("menyakiti diri")
                        }

                        append(". Ini sangat serius dan perlu ditangani oleh ")

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xffB82B55),
                                fontWeight = FontWeight.Bold
                            )
                        ){
                            append("professional")
                        }

                        append(". Ingatlah untuk menjaga kesehatan mental. Jangan ragu mencari dukungan dari ")

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xffB82B55),
                                fontWeight = FontWeight.Bold
                            )
                        ){
                            append("orang terdekat")
                        }

                        withStyle(
                            style = SpanStyle(
//                                color = Color(0xffB82B55),
                                fontWeight = FontWeight.Bold
                            )
                        ){
                            append(". Banyak yang peduli dan siap membantumu")
                        }

                        append("mengatasi kesulitan yang kamu hadapi.")
                    },
//                        "Kami mendeteksi adanya beberapa kalimat yang " +
//                            "mengindikasikan potensi adanya perasaan ingin " +
//                            "menyakiti diri. Ini sangat serius dan perlu ditangani " +
//                            "oleh profesional. Ingatlah untuk menjaga kesehatan mental. " +
//                            "Jangan ragu mencari dukungan dari orang lain. " +
//                            "Banyak yang peduli dan siap membantumu mengatasi kesulitan " +
//                            "yang kamu hadapi.",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    }
}