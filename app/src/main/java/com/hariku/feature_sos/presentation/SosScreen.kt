package com.hariku.feature_sos.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
fun SosScreen(navController: NavController) {
    Scaffold(
        containerColor = Color(0xFFFFFFFF),
        topBar = { SosTopBar() },
        bottomBar = { HomeIndicator() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(180.dp))

            Text(
                text = "Apakah kamu dalam keadaan darurat?",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Kamu tidak sendiri.",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF242424)
            )

            Text(
                text = "Dapatkan bantuan hanya dalam satu panggilan.",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF242424)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Meminta bantuan bukan berarti kamu lemah.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFB55D6C),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .background(Color.Transparent)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Coba Teknik 5 Panca Indra",
                    color = Color(0xFFB55D6C),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xFFB55D6C))
                    .clickable {
                        navController.navigate(Routes.SosProfessional.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bantuan Profesional",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun SosTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Kembali",
                    modifier = Modifier.size(70.dp)
                )
            }
        }
    }
}

@Composable
fun HomeIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 8.dp),
        contentAlignment = Alignment.Center
    ){}
}

@Preview(showBackground = true, widthDp = 430, heightDp = 932)
@Composable
fun SosScreenPreview() {
    MaterialTheme {
        SosScreen(navController = NavController(LocalContext.current) )
    }
}
