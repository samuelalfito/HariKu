package com.hariku.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hariku.R
import com.hariku.core.ui.theme.Coral70
import com.hariku.core.ui.theme.Neutral100

@Composable
fun FloatingActButton(
    label: String,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = { onClick() },
        shape = RoundedCornerShape(50.dp),
        containerColor = Coral70,
        modifier = Modifier.size(72.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_tambah_obrolan),
                contentDescription = label,
                modifier = Modifier.size(72.dp)
            )
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = label,
                tint = Neutral100,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}
