package com.hariku.feature_article.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hariku.feature_article.domain.model.Article

class ArticleViewModel : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _articles = mutableStateListOf<Article>()

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun setArticles(list: List<Article>) {
        _articles.clear()
        _articles.addAll(list)
    }

    val filteredArticles: List<Article>
        get() {
            val query = _searchQuery.value.trim()
            return if (query.isEmpty()) {
                _articles
            } else {
                _articles.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.category.contains(query, ignoreCase = true)
                }
            }
        }
}