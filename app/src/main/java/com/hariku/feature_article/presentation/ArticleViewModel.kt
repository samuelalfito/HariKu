package com.hariku.feature_article.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hariku.feature_article.domain.model.Article
import com.hariku.feature_article.domain.model.ArticleCategory
import com.hariku.feature_article.domain.usecase.GetArticleByIdUseCase
import com.hariku.feature_article.domain.usecase.GetArticlesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

sealed class ArticleUiState {
    object Loading : ArticleUiState()
    data class Success(val articles: List<Article>) : ArticleUiState()
    data class Error(val message: String) : ArticleUiState()
}

class ArticleViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<ArticleUiState>(ArticleUiState.Loading)
    val uiState: StateFlow<ArticleUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery
    
    private val _allArticles = mutableStateOf<List<Article>>(emptyList())
    
    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle.asStateFlow()
    
    init {
        loadArticles()
    }
    
    fun loadArticles() {
        viewModelScope.launch {
            _uiState.value = ArticleUiState.Loading
            try {
                getArticlesUseCase()
                    .catch { exception ->
                        _uiState.value = ArticleUiState.Error(exception.message ?: "Unknown error")
                    }
                    .collect { articles ->
                        _allArticles.value = articles
                        _uiState.value = ArticleUiState.Success(articles)
                    }
            } catch (e: Exception) {
                _uiState.value = ArticleUiState.Error(e.message ?: "Failed to load articles")
            }
        }
    }
    
    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
    
    fun getArticleById(id: String) {
        viewModelScope.launch {
            try {
                val article = getArticleByIdUseCase(id)
                _selectedArticle.value = article
            } catch (e: Exception) {
                Log.e("ArticleViewModel", "Error loading article: ${e.message}", e)
                _selectedArticle.value = null
            }
        }
    }
    
    fun getFilteredArticlesByCategory(category: String): List<Article> {
        return _allArticles.value.filter { it.category == category }
    }
    
    val filteredArticles: List<Article>
        get() {
            val query = _searchQuery.value.trim()
            return if (query.isEmpty()) {
                _allArticles.value
            } else {
                _allArticles.value.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.category.contains(query, ignoreCase = true)
                }
            }
        }
    
    val filteredCategories: List<ArticleCategory>
        get() {
            val query = _searchQuery.value.trim()
            val allCategories = getAllCategories()
            return if (query.isEmpty()) {
                allCategories
            } else {
                allCategories.filter { category ->
                    category.name.contains(query, ignoreCase = true)
                }
            }
        }
    
    private fun getAllCategories(): List<ArticleCategory> {
        return _allArticles.value
            .map { it.category }
            .distinct()
            .map { categoryName ->
                val imageRes = getCategoryImageRes(categoryName) ?: com.hariku.R.drawable.cat
                ArticleCategory(
                    name = categoryName,
                    imageRes = imageRes
                )
            }
    }
    
    private fun getCategoryImageRes(category: String): Int? {
        return when (category.lowercase()) {
            "stres" -> com.hariku.R.drawable.ic_kategori_stres
            "kecemasan" -> com.hariku.R.drawable.ic_kategori_kecemasan
            "motivasi diri" -> com.hariku.R.drawable.ic_kategori_motivasi_diri
            "tidur" -> com.hariku.R.drawable.ic_kategori_tidur
            "depresi" -> com.hariku.R.drawable.ic_kategori_depresi
            "kesadaran penuh" -> com.hariku.R.drawable.ic_kategori_kesadaran_penuh
            "strategi coping" -> com.hariku.R.drawable.ic_kategori_strategi_coping
            "hubungan" -> com.hariku.R.drawable.ic_kategori_hubungan
            else -> null
        }
    }
}