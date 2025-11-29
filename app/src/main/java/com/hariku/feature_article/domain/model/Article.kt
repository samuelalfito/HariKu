package com.hariku.feature_article.domain.model

data class Article(
    val id: String,
    val title: String,
    val category: String,
    val content: String,
    val imageUrl: String,
    val uploadedAt: Long,
    val author: String = "Anonymous",
    val readTime: String = "5 Menit"
)