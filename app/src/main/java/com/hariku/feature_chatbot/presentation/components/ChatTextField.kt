package com.hariku.feature_chatbot.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hariku.R

@Composable
fun ChatTextFieldBar(text: String, onTextChanged: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Kolom Teks
            TextField(
                value = text,
                onValueChange = onTextChanged,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ketik Pesan Anda") },
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color(0xFFFDE8D8),
                    unfocusedContainerColor = Color(0xFFFDE8D8)
                )
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFD9A188), CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = "Kirim",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 8.dp)
        ) {
        }
    }
}