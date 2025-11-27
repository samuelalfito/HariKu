package com.hariku.feature_article.domain.repository

import com.hariku.feature_article.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getArticles(): Flow<List<Article>>
    suspend fun getArticleById(id: String): Article?
    suspend fun getArticlesByCategory(category: String): List<Article>
    suspend fun searchArticles(query: String): List<Article>
}