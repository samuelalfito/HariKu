package com.hariku.feature_article.data.repository

import com.hariku.feature_article.data.mapper.ArticleMapper
import com.hariku.feature_article.data.remote.ArticleRemoteDataSource
import com.hariku.feature_article.domain.model.Article
import com.hariku.feature_article.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticleRepositoryImpl(
    private val remoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {
    
    override suspend fun getArticles(): Flow<List<Article>> {
        return remoteDataSource.observeArticles()
            .map { dtos ->
                dtos.map { ArticleMapper.toDomain(it) }
            }
    }
    
    override suspend fun getArticleById(id: String): Article? {
        val remoteArticle = remoteDataSource.getArticleById(id)
        return remoteArticle?.let { ArticleMapper.toDomain(it) }
    }
    
    override suspend fun getArticlesByCategory(category: String): List<Article> {
        val remoteDtos = remoteDataSource.getArticlesByCategory(category)
        return remoteDtos.map { ArticleMapper.toDomain(it) }
    }
    
    override suspend fun searchArticles(query: String): List<Article> {
        return emptyList()
    }
}