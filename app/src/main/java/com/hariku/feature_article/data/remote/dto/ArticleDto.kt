package com.hariku.feature_article.data.remote.dto

data class ArticleDto(
    val id: String? = null,
    val title: String? = null,
    val category: String? = null,
    val content: String? = null,
    val imageUrl: String? = null,
    val uploadedAt: Long? = null,
    val author: String? = null,
    val readTime: String? = null
)