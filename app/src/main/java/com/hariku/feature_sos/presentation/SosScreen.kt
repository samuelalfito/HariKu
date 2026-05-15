package com.hariku.feature_sos.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hariku.R
import com.hariku.core.ui.components.Routes
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.Rose35

@Composable
fun SosScreen(navController: NavController) {
    Scaffold(
        containerColor = AdaptiveColors.adaptiveBackground()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AdaptiveColors.adaptiveBackground())
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Back"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 45.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = "Apakah kamu dalam keadaan darurat?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AdaptiveColors.adaptiveTextSecondary(),
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "Kamu tidak sendiri.\nDapatkan bantuan hanya dalam satu panggilan.",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = AdaptiveColors.adaptiveText()
                )
                
                Text(
                    text = "Meminta bantuan bukan berarti kamu lemah.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AdaptiveColors.adaptiveTextSecondary(),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(0.dp))
                
                OutlinedButton(
                    onClick = { navController.navigate(Routes.Senses.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(3.dp, Rose35)
                ) {
                    Text(
                        text = "Coba Teknik 5 Panca Indra",
                        color = Rose35,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                
                Button(
                    onClick = { navController.navigate(Routes.SosProfessional.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Rose35,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Bantuan Profesional",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SosScreenPreview() {
    MaterialTheme {
        SosScreen(navController = NavController(LocalContext.current))
    }
}
