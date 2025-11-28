package com.hariku.feature_article.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hariku.feature_article.presentation.components.ArticleTopBar
import com.hariku.feature_article.presentation.search.SearchEmpty
import com.hariku.feature_article.presentation.search.SearchResult
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArticleScreen(
    viewModel: ArticleViewModel = koinViewModel(),
    onArticleClick: (String) -> Unit = {},
    onCategoryClick: (String) -> Unit = {}
) {
    val searchQuery by viewModel.searchQuery
    val uiState by viewModel.uiState.collectAsState()
    val filteredArticles = viewModel.filteredArticles

    Scaffold(
        topBar = {
            ArticleTopBar(
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            is ArticleUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFC97D50))
                }
            }
            is ArticleUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (uiState as ArticleUiState.Error).message,
                        color = Color.Red
                    )
                }
            }
            is ArticleUiState.Success -> {
                if (searchQuery.isBlank()) {
                    SearchEmpty(
                        contentPadding = padding,
                        onArticleClick = onArticleClick,
                        onCategoryClick = onCategoryClick,
                        viewModel = viewModel
                    )
                } else {
                    SearchResult(
                        filteredArticles = filteredArticles,
                        filteredCategories = viewModel.filteredCategories,
                        viewModel = viewModel,
                        contentPadding = padding,
                        onArticleClick = onArticleClick,
                        onCategoryClick = onCategoryClick
                    )
                }
            }
        }
    }
}