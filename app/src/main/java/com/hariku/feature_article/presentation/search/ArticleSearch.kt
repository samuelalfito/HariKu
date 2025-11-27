package com.hariku.feature_article.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.feature_article.domain.model.Article
import com.hariku.feature_article.domain.model.ArticleCategory
import com.hariku.feature_article.presentation.ArticleUiState
import com.hariku.feature_article.presentation.ArticleViewModel
import com.hariku.feature_article.presentation.components.ArticleCard
import com.hariku.feature_article.presentation.components.CategoryCard

@Composable
fun SearchEmpty(
    contentPadding: PaddingValues = PaddingValues(),
    onArticleClick: (String) -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
    viewModel: ArticleViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val articles = when (val state = uiState) {
        is ArticleUiState.Success -> state.articles
        else -> emptyList()
    }
    
    val categories = articles
        .map { it.category }
        .distinct()
        .map { categoryName ->
            val imageRes = getCategoryImageRes(categoryName) ?: R.drawable.cat
            ArticleCategory(name = categoryName, imageRes = imageRes)
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Artikel Pilihan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Text(
                    text = "Pahami topik kesehatan mental dengan lebih baik.",
                    fontSize = 12.sp,
                    color = Color(0xFF9F9F9F)
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(articles.take(5)) { article ->
                        ArticleCard(
                            article = article,
                            modifier = Modifier.width(250.dp),
                            onClick = { onArticleClick(article.id) }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Kategori",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 520.dp),
                    userScrollEnabled = false
                ) {
                    items(categories) { category ->
                        CategoryCard(
                            imageRes = category.imageRes,
                            categoryName = category.name,
                            onClick = { onCategoryClick(category.name) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

private fun getCategoryImageRes(category: String): Int? {
    return when (category.lowercase()) {
        "stres" -> R.drawable.ic_kategori_stres
        "kecemasan" -> R.drawable.ic_kategori_kecemasan
        "motivasi diri" -> R.drawable.ic_kategori_motivasi_diri
        "tidur" -> R.drawable.ic_kategori_tidur
        "depresi" -> R.drawable.ic_kategori_depresi
        "kesadaran penuh" -> R.drawable.ic_kategori_kesadaran_penuh
        "strategi coping" -> R.drawable.ic_kategori_strategi_coping
        "hubungan" -> R.drawable.ic_kategori_hubungan
        else -> null
    }
}

@Composable
fun SearchResult(
    filteredArticles: List<Article>,
    filteredCategories: List<ArticleCategory>,
    viewModel: ArticleViewModel,
    contentPadding: PaddingValues = PaddingValues(),
    onArticleClick: (String) -> Unit = {},
    onCategoryClick: (String) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding
        ) {
            if (filteredCategories.isEmpty() && filteredArticles.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = Color(0xFFBDBDBD)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Artikel tidak ditemukan",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Coba kata kunci lain atau bersihkan pencarian.",
                                fontSize = 16.sp,
                                color = Color(0xFF9F9F9F)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.onSearchQueryChange("") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFE1A071),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Bersihkan Pencarian")
                            }
                        }
                    }
                }
            } else {
                if (filteredCategories.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Kategori",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    
                    item {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 300.dp),
                            userScrollEnabled = false
                        ) {
                            items(filteredCategories) { category ->
                                CategoryCard(
                                    imageRes = category.imageRes,
                                    categoryName = category.name,
                                    onClick = { onCategoryClick(category.name) }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                
                if (filteredArticles.isNotEmpty()) {
                    item {
                        Text(
                            text = "Artikel",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    
                    items(filteredArticles) { article ->
                        ArticleCard(
                            article = article,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onArticleClick(article.id) }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}