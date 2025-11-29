package com.hariku.feature_article.domain.usecase

import com.hariku.feature_article.domain.model.Article
import com.hariku.feature_article.domain.repository.ArticleRepository

class GetArticleByIdUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(id: String): Article? {
        return repository.getArticleById(id)
    }
}