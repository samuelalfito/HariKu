package com.hariku.feature_sos.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hariku.R
import com.hariku.feature_sos.presentation.components.HotlineButton
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SosProfessionalScreen(navController: NavController) {
    val context = LocalContext.current
    
    Scaffold(
        containerColor = Color(0xFFFFFFFF)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    text = "Memiliki bantuan yang tepat pada waktu yang tepat dapat membantumu kembali lebih kuat.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "Jika kamu memerlukan bantuan atau seseorang untuk diajak bicara, berikut adalah daftar hotline",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                
                HotlineButton(
                    title = "Hotline Pemerintah",
                    prefix = "Hotline 24 jam: ",
                    hotline = "119",
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:119")
                        context.startActivity(intent)
                    }
                )
                
                HotlineButton(
                    title = "Kementrian Kesehatan",
                    prefix = "Hotline: ",
                    hotline = "500-454",
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:500454")
                        context.startActivity(intent)
                    }
                )
                
                HotlineButton(
                    title = "Save Yourselves Indonesia (Jakarta)",
                    prefix = "Hotline: ",
                    hotline = "082124326459",
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:082124326459")
                        context.startActivity(intent)
                    }
                )
            }
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