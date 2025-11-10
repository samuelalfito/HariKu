package com.hariku.feature_article.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hariku.R
import com.hariku.core.ui.theme.HariKuTheme
import com.hariku.feature_article.domain.model.Article
import com.hariku.feature_article.presentation.components.ArticleTopBar
import com.hariku.feature_article.presentation.search.SearchEmpty
import com.hariku.feature_article.presentation.search.SearchResult

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(viewModel: ArticleViewModel = viewModel()) {
    val searchQuery by viewModel.searchQuery
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
        if (searchQuery.isBlank()) {
            SearchEmpty(contentPadding = padding)
        } else {
            SearchResult(filteredArticles, viewModel, contentPadding = padding)
        }
    }
}

val sampleArticles = listOf(
    Article(
        id = 1,
        title = "Kendalikan Kekhawatiranmu!",
        category = "Kecemasan",
        readTime = "5 Menit",
        imageRes = R.drawable.cat
    ),
    Article(
        id = 2,
        title = "5 Hal yang Dapat Membantu Kecemasanmu!",
        category = "Depresi",
        readTime = "5 Menit",
        imageRes = R.drawable.cat
    ),
    Article(
        id = 3,
        title = "Cara Mengatasi Stres di Tempat Kerja",
        category = "Stres",
        readTime = "7 Menit",
        imageRes = R.drawable.cat
    )
)

val categories = listOf(
    R.drawable.ic_kategori_stres,
    R.drawable.ic_kategori_kecemasan,
    R.drawable.ic_kategori_motivasi_diri,
    R.drawable.ic_kategori_tidur,
    R.drawable.ic_kategori_depresi,
    R.drawable.ic_kategori_kesadaran_penuh,
    R.drawable.ic_kategori_strategi_coping,
    R.drawable.ic_kategori_hubungan
)

@Preview
@Composable
private fun ArticleScreenPreview() {
    HariKuTheme {
        val previewViewModel = ArticleViewModel().apply { setArticles(sampleArticles) }
        ArticleScreen(viewModel = previewViewModel)
    }
}