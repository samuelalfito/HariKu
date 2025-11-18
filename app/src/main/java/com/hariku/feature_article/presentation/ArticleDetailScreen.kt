package com.hariku.feature_article.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.core.ui.theme.HariKuTheme

@Composable
fun ArticleDetailScreen(
    articleIndex: Int = 1
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .padding(horizontal = 26.dp)
    ) {
        val article = sampleArticles.getOrNull(articleIndex)

        item {
            Icon(
                painterResource(id = R.drawable.back_arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(48.dp)
                    .offset(x = (-24).dp)
                    .clickable { },
                tint = Color(0xFFC97D50)
            )
        }

        item {
            Text(
                text = "${article?.title}",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Text(
                text = "${article?.readTime}" + " • oleh " + "${article?.author}",
                fontSize = 12.sp,
                color = Color(0xFF9F9F9F),
            )
        }

        item {
            Image(
                painter = painterResource(id = R.drawable.cat),
                contentDescription = "Kecemasan Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }

        item {
            Text(
                text = dummyContent(),
                fontSize = 16.sp,
            )
        }
    }
}

fun dummyContent(): String = """
    Lorem ipsum dolor sit amet, consectetur

    adipiscing elit. Ut et massa mi. Aliquam in hendrerit urna. 
    Pellentesque sit amet sapien fringilla, mattis ligula consectetur, ultrices mauris. 
    Maecenas vitae mattis tellus. Nullam quis imperdiet augue. Vestibulum auctor ornare leo, 
    non suscipit magna interdum eu. Curabitur pellentesque nibh nibh, at maximus ante fermentum sit amet. 
    
    Pellentesque commodo lacus at sodales sodales. Quisque sagittis orci ut diam condimentum, vel euismod erat placerat. 
    In iaculis arcu eros, eget tempus orci facilisis id.Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
    Ut et massa mi. Aliquam in hendrerit urna. Pellentesque sit amet sapien fringilla, mattis ligula consectetur, 
    ultrices mauris. Maecenas vitae mattis tellus.
""".trimIndent()

@Preview
@Composable
private fun ArticleDetailScreenPreview() {
    HariKuTheme {
        ArticleDetailScreen()
    }
}