package com.hariku.feature_article.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hariku.feature_article.domain.model.Category

@Composable
fun CategoryCard(category: Category) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = category.iconRes),
            contentDescription = "Category Background",
            modifier = Modifier.matchParentSize()
        )
    }
}