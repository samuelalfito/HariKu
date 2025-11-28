package com.hariku.feature_article.domain.usecase

import com.hariku.feature_article.domain.model.Article
import com.hariku.feature_article.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetArticlesUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(): Flow<List<Article>> {
        return repository.getArticles()
    }
}