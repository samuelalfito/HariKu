package com.hariku.feature_article.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.feature_article.presentation.components.ArticleItem
import org.koin.androidx.compose.koinViewModel
import com.hariku.core.ui.theme.Coral30
import com.hariku.core.ui.theme.AdaptiveColors

@Composable
fun ArticleListScreen(
    category: String,
    viewModel: ArticleViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    onArticleClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val articles = viewModel.getFilteredArticlesByCategory(category)
    val categoryImageRes = getCategoryImageResForList(category)

    Column(
        modifier = Modifier.fillMaxSize().background(AdaptiveColors.adaptiveBackground())
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = categoryImageRes),
                contentDescription = "$category Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(1.2f),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(90.dp)
                        .scale(1.5f),
                    tint = Color.White // Keep arrow white against the image
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-24).dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(AdaptiveColors.adaptiveBackground())
        ) {
            when (uiState) {
                is ArticleUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Coral30)
                    }
                }
                is ArticleUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (uiState as ArticleUiState.Error).message,
                            color = Coral30
                        )
                    }
                }
                is ArticleUiState.Success -> {
                    if (articles.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Tidak ada artikel di kategori $category",
                                fontSize = 16.sp,
                                color = AdaptiveColors.adaptiveTextSecondary()
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = category,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = AdaptiveColors.adaptiveText(),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            
                            articles.forEach { article ->
                                ArticleItem(
                                    title = article.title,
                                    time = article.readTime,
                                    author = "oleh ${article.author}",
                                    imageUrl = article.imageUrl,
                                    onClick = { onArticleClick(article.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getCategoryImageResForList(category: String): Int {
    return when (category.lowercase()) {
        "stres" -> R.drawable.ic_kategori_stres
        "kecemasan" -> R.drawable.ic_kategori_kecemasan
        "motivasi diri" -> R.drawable.ic_kategori_motivasi_diri
        "tidur" -> R.drawable.ic_kategori_tidur
        "depresi" -> R.drawable.ic_kategori_depresi
        "kesadaran penuh" -> R.drawable.ic_kategori_kesadaran_penuh
        "strategi coping" -> R.drawable.ic_kategori_strategi_coping
        "hubungan" -> R.drawable.ic_kategori_hubungan
        else -> R.drawable.cat
    }
}
