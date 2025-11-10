package com.hariku.feature_article.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.core.ui.theme.HariKuTheme
import com.hariku.feature_article.domain.model.Article
import com.hariku.feature_article.presentation.components.ArticleCard
import com.hariku.feature_article.presentation.components.CategoryCard

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
            )},
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            if (searchQuery.isBlank()) {
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
                        items(sampleArticles) { article ->
                            ArticleCard(article = article, modifier = Modifier.width(250.dp))
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
                        modifier = Modifier.height(520.dp),
                        userScrollEnabled = false
                    ) {
                        items(categories) { imageRes ->
                            CategoryCard(imageRes)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            } else {
                if (filteredArticles.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 40.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 32.dp)
                                    .align(Alignment.TopCenter),
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
                    items(filteredArticles) { article ->
                        Spacer(modifier = Modifier.height(24.dp))
                        ArticleCard(article = article, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
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
                    .statusBarsPadding()
                    .clickable { }
            )
        },
        title = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(end = 28.dp)) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .align(Alignment.CenterStart)
                        .padding(horizontal = 0.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFE1A071)
        ),
    )
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search_lens),
                contentDescription = "Search Icon",
                modifier = Modifier.size(12.dp),
                tint = Color(0xFF9F9F9F)
            )

            Spacer(modifier = Modifier.width(6.dp))

            BasicTextField(
                value = query,
                onValueChange = { onQueryChange(it) },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (query.isEmpty()) {
                            Text(
                                text = "Cari Artikel",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF9F9F9F)
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

            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Clear",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
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