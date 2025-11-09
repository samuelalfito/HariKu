package com.hariku.feature_article.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.core.ui.theme.HariKuTheme
import com.hariku.feature_article.domain.model.Article
import com.hariku.feature_article.presentation.ArticleScreen

@Composable
fun ArticleCard(
    article: Article
) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(240.dp)
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Image(
                painter = painterResource(id = article.imageRes),
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.title,
                    fontSize = 16.sp,
                    color = Color(0xFF333333),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${article.category} • ${article.readTime}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9F9F9F)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ArticleCardPreview() {
    HariKuTheme {
        val sample = Article(
            id = 1,
            title = "Kendalikan Kekhawatiranmu",
            category = "Kecemasan",
            readTime = "5 Menit",
            imageRes = R.drawable.cat
        )
        ArticleCard(article = sample)
    }
}