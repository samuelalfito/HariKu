package com.hariku.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hariku.R

@Composable
fun BottomNavBar(selectedIndex: Int = 0) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            iconSelected = R.drawable.ic_home_fill,
            iconUnselected = R.drawable.ic_home,
            selected = selectedIndex == 0,
            contentDescription = "Home"
        )
        BottomNavItem(
            iconSelected = R.drawable.ic_chat_fill,
            iconUnselected = R.drawable.ic_chat,
            selected = selectedIndex == 1,
            contentDescription = "Chat"
        )
        BottomNavItem(
            iconSelected = R.drawable.ic_journal_fill,
            iconUnselected = R.drawable.ic_journal,
            selected = selectedIndex == 2,
            contentDescription = "Journal"
        )
        BottomNavItem(
            iconSelected = R.drawable.ic_statistic_fill,
            iconUnselected = R.drawable.ic_statistic,
            selected = selectedIndex == 3,
            contentDescription = "Stats"
        )
    }
}

@Composable
fun BottomNavItem(
    iconSelected: Int,
    iconUnselected: Int,
    selected: Boolean,
    contentDescription: String
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = if (selected) iconSelected else iconUnselected),
            contentDescription = contentDescription,
            tint = Color.Unspecified,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    BottomNavBar(selectedIndex = 0)
}