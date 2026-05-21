package com.hariku.feature_pin.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.R
import com.hariku.core.ui.components.Routes
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.feature_pin.presentation.components.NumpadComposable
import com.hariku.feature_pin.presentation.components.PinDotsComposable

@Composable
fun FillPinScreen(navController: NavController) {
    var pinValue by remember { mutableStateOf("") }
    val maxPinLength = 4
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AdaptiveColors.adaptiveBackground())
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Lewati",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = AdaptiveColors.adaptiveText(),
                        textDecoration = TextDecoration.Underline,
                    ),
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Routes.Home.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Masukkan Pin",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    color = AdaptiveColors.adaptiveText(),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(horizontal = 40.dp)
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            PinDotsComposable(
                count = maxPinLength,
                filled = pinValue.length
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            NumpadComposable(
                onNumberClick = { number ->
                    if (pinValue.length < maxPinLength) {
                        pinValue += number.toString()
                    }
                    if (pinValue.length == maxPinLength) {
                        navController.navigate(Routes.Home.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                onBackspaceClick = {
                    if (pinValue.isNotEmpty()) {
                        pinValue = pinValue.dropLast(1)
                    }
                }
            )
            
            Spacer(modifier = Modifier.weight(1f))
        }
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .align(Alignment.BottomCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.kocheng),
                contentDescription = "Ilustrasi Bawah",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 430, heightDp = 932)
@Composable
fun FIllPinScreenPreview() {
    FillPinScreen(rememberNavController())
}

