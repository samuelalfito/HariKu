package com.hariku.feature_article.domain.model

import androidx.compose.ui.graphics.Color

data class Article(
    val id: Int,
    val title: String,
    val category: String,
    val readTime: String,
)

data class Category(
    val id: Int,
    val name: String,
    val color: Color
)
