package com.hariku.feature_article.domain.usecase

import com.hariku.feature_article.domain.model.Article
import com.hariku.feature_article.domain.repository.ArticleRepository

class SearchArticlesUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(query: String): List<Article> {
        return repository.searchArticles(query)
    }
}