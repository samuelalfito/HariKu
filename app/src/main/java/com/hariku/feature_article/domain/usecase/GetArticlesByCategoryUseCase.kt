package com.hariku.feature_article.domain.usecase

import com.hariku.feature_article.domain.model.Article
import com.hariku.feature_article.domain.repository.ArticleRepository

class GetArticlesByCategoryUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(category: String): List<Article> {
        return repository.getArticlesByCategory(category)
    }
}