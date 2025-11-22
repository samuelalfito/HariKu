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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hariku.R


@Composable
fun SosProfessionalScreen(navController: NavController) {
    Scaffold(
        containerColor = Color(0xFFFFFFFF),
        topBar = { SosProTopBar(navController) },
        bottomBar = { SosProHomeIndicator() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(130.dp))

            Text(
                text = "Memiliki bantuan yang tepat pada waktu yang tepat dapat membantumu kembali lebih kuat.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Jika kamu memerlukan bantuan atau seseorang untuk diajak bicara, berikut adalah daftar hotline",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            HotlineButton(
                title = "Hotline Pemerintah",
                prefix = "Hotline 24 jam: ",
                hotline = "119"
            )

            Spacer(modifier = Modifier.height(16.dp))

            HotlineButton(
                title = "Kementrian Kesehatan",
                prefix = "Hotline: ",
                hotline = "500-454"
            )

            Spacer(modifier = Modifier.height(16.dp))

            HotlineButton(
                title = "Save Yourselves Indonesia (Jakarta)",
                prefix = "Hotline: ",
                hotline = "082124326459"
            )
        }
    }
}


@Composable
fun HotlineButton(
    title: String,
    prefix: String,
    hotline: String
) {
    val hotlineColor = Color(0xFFB55D6C)
    val textHotlineColor = Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = hotlineColor,
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.Transparent)
            .clickable { }
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                color = textHotlineColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Bold)) {
                        append(prefix)
                    }
                    withStyle(style = SpanStyle(
                        color = hotlineColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    )) {
                        append(hotline)
                    }
                }
            )
        }
    }
}

@Composable
fun SosProTopBar(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
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
fun SosProHomeIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
    }
}

@Preview(showBackground = true, widthDp = 430, heightDp = 932)
@Composable
fun SosProfessionalScreenPreview() {
    MaterialTheme {
        SosProfessionalScreen(navController = NavController(LocalContext.current))
    }
}
