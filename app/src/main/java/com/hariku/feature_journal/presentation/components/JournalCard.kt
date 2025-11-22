package com.hariku.feature_journal.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R

@Composable
fun JournalCard(title: String, bgRes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(4f / 5f) // Tetapkan rasio aspek agar tingginya proporsional
            .clip(RoundedCornerShape(20.dp)) // Untuk sudut tumpul
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = bgRes),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp)
                .matchParentSize(),
            contentScale = ContentScale.Fit
        )
        
        Image(
            painter = painterResource(id = R.drawable.ic_pin_journal),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
        )
        
        // 3. Teks (Di tengah Box)
        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Center) // Posisikan di tengah Box
        )
    }
}

@Preview
@Composable
private fun Preview() {
    JournalCard(title = "STRES", bgRes = R.drawable.img_pink_bg, onClick = {})
}