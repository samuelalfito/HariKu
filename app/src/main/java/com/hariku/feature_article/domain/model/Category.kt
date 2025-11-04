package com.hariku.feature_article.domain.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Category(
    val id: Int,
    val name: String,
    @DrawableRes val iconRes: Int
)