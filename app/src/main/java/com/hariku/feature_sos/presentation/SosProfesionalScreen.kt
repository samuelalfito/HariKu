package com.hariku.feature_sos.presentation

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hariku.R
import com.hariku.feature_sos.presentation.components.HotlineButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.hariku.core.ui.theme.AdaptiveColors

@Composable
fun SosProfessionalScreen(navController: NavController) {
    val context = LocalContext.current
    
    Scaffold(
        containerColor = AdaptiveColors.adaptiveBackground(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Bantuan Profesional",
                    style = MaterialTheme.typography.titleMedium,
                    color = AdaptiveColors.adaptiveText(),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Memiliki bantuan yang tepat pada waktu yang tepat dapat membantumu kembali lebih kuat.",
                style = MaterialTheme.typography.bodyLarge,
                color = AdaptiveColors.adaptiveText(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Jika kamu memerlukan bantuan atau seseorang untuk diajak bicara, berikut adalah daftar hotline",
                style = MaterialTheme.typography.bodyLarge,
                color = AdaptiveColors.adaptiveTextSecondary(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            HotlineButton(
                title = "Hotline Pemerintah",
                prefix = "Hotline 24 jam: ",
                hotline = "119",
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = "tel:119".toUri()
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            HotlineButton(
                title = "Kementrian Kesehatan",
                prefix = "Hotline: ",
                hotline = "500-454",
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = "tel:500454".toUri()
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            HotlineButton(
                title = "Save Yourselves Indonesia (Jakarta)",
                prefix = "Hotline: ",
                hotline = "082124326459",
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = "tel:082124326459".toUri()
                    context.startActivity(intent)
                }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
fun SosProfessionalScreenPreview() {
    MaterialTheme {
        SosProfessionalScreen(navController = NavController(LocalContext.current))
    }
}
