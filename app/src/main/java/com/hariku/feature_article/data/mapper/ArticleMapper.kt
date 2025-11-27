package com.hariku.feature_article.data.mapper

import com.hariku.feature_article.data.remote.dto.ArticleDto
import com.hariku.feature_article.domain.model.Article

object ArticleMapper {
    
    fun toDomain(dto: ArticleDto): Article {
        return Article(
            id = dto.id.orEmpty(),
            title = dto.title.orEmpty(),
            category = dto.category.orEmpty(),
            content = dto.content.orEmpty(),
            imageUrl = dto.imageUrl.orEmpty(),
            uploadedAt = dto.uploadedAt ?: 0L,
            author = dto.author ?: "Anonymous",
            readTime = dto.readTime ?: "5 Menit"
        )
    }
}