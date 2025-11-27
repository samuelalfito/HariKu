package com.hariku.feature_article.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hariku.R
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ArticleDetailScreen(
    articleId: String,
    viewModel: ArticleViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val article by viewModel.selectedArticle.collectAsState()
    
    LaunchedEffect(articleId) {
        viewModel.getArticleById(articleId)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .padding(horizontal = 26.dp)
    ) {
        item {
            Icon(
                painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(48.dp)
                    .offset(x = (-24).dp)
                    .clickable { onBackClick() },
                tint = Color(0xFFC97D50)
            )
        }

        if (article == null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFC97D50))
                }
            }
        } else {
            item {
                Text(
                    text = article?.title ?: "",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            item {
                val uploadDate = article?.uploadedAt?.let {
                    SimpleDateFormat("dd MMM yyyy", Locale.forLanguageTag("id-ID")).format(Date(it))
                } ?: ""
                
                Text(
                    text = "${article?.readTime} • $uploadDate • oleh ${article?.author}",
                    fontSize = 12.sp,
                    color = Color(0xFF9F9F9F),
                )
            }

            item {
                if (article?.imageUrl?.isNotEmpty() == true) {
                    AsyncImage(
                        model = article?.imageUrl,
                        contentDescription = article?.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = R.drawable.cat)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.cat),
                        contentDescription = article?.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                Text(
                    text = article?.content ?: "",
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }
    }
}