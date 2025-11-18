package com.hariku.feature_chatbot.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.feature_chatbot.domain.model.ChatbotData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailTopBar(chatbotId: String, onBackClick: () -> Unit, onSosClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onBackClick()
            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = "Kembali",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_avatar_hariku),
                    contentDescription = "Avatar Hariku",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = chatbotId,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            Button(
                onClick = { onSosClick() },
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF68674)
                ),
                shape = RoundedCornerShape(size = 25.dp)
            ) {
                Text(
                    text = "SOS",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFDFCFC)
                    )
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        expandedHeight = 80.dp
    )
}