package com.hariku.feature_article.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.core.ui.theme.HariKuTheme
import com.hariku.feature_article.domain.model.Article
import com.hariku.feature_article.domain.model.Category
import com.hariku.feature_article.presentation.components.ArticleCard
import com.hariku.feature_article.presentation.components.CategoryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen() {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(72.dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                navigationIcon = {
                    Icon(
                        painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { }
                    )
                },
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(end = 28.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.search_lens),
                                contentDescription = "Search Icon",
                                modifier = Modifier.size(12.dp),
                                tint = Color(0xFF9F9F9F)
                            )
                            Spacer(modifier = Modifier.width(6.dp))

                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (searchQuery.isEmpty()) {
                                            Text(
                                                text = "Cari Artikel",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color.Gray
                                            )
                                        }
                                        innerTextField()
                                    }
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Search
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = {  }
                                )
                            )

                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = "Clear",
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE1A071)
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
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
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(sampleArticles) { article ->
                        ArticleCard(article = article)
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
                    modifier = Modifier.height(500.dp),
                    userScrollEnabled = false
                ) {
                    items(categories) { category ->
                        CategoryCard(category = category)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

val sampleArticles = listOf(
    Article(
        id = 1,
        title = "Kendalikan Kekhawatiranmu",
        category = "Kecemasan",
        readTime = "5 Menit"
    ),
    Article(
        id = 2,
        title = "5 Hal yang Dapat Memicu Depresi",
        category = "Depresi",
        readTime = "5 Menit"
    ),
    Article(
        id = 3,
        title = "Cara Mengatasi Stres di Tempat Kerja",
        category = "Stres",
        readTime = "7 Menit"
    )
)

val categories = listOf(
    Category(1, "STRES", Color(0xFFFF9999)),
    Category(2, "KECEMASAN", Color(0xFFB39DDB)),
    Category(3, "MOTIVASI\nDIRI", Color(0xFFFFB74D)),
    Category(4, "TIDUR", Color(0xFF90A4AE)),
    Category(5, "DEPRESI", Color(0xFF7E57C2)),
    Category(6, "MINDFULNESS", Color(0xFF81C784))
)

@Preview
@Composable
private fun ArticleScreenPreview() {
    HariKuTheme {
        ArticleScreen()
    }
}