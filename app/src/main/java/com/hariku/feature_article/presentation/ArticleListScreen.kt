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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hariku.R
import com.hariku.feature_article.presentation.components.ArticleItem

@Composable
fun KecemaasanScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_kategori_kecemasan),
                contentDescription = "Kecemasan Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(1.2f),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = {  },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(90.dp)
                        .scale(1.5f)
                )
            }
        }

        // Content Articles
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-24).dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)) // rounded atas aja
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ArticleItem(
                    title = "5 Hal yang Dapat Membantu Kecemaasanmu!",
                    time = "5 Menit",
                    author = "oleh Anonymous",
                    imageResId = R.drawable.cat
                )

                ArticleItem(
                    title = "Kendalikan Kekhawatiranmu!",
                    time = "5 Menit",
                    author = "oleh Anonymous",
                    imageResId = R.drawable.cat
                )

                ArticleItem(
                    title = "Perfeksionisme Yang Sering Bikin Cemas [Trigger Warning]",
                    time = "5 Menit",
                    author = "oleh Anonymous",
                    imageResId = R.drawable.cat
                )

                ArticleItem(
                    title = "Quarter Life Crisis - Cemas Mengenai Kehidupan Di Masa Depan [Trigger Warning]",
                    time = "5 Menit",
                    author = "oleh Anonymous",
                    imageResId = R.drawable.cat
                )
            }
        }
    }
}

@Preview
@Composable
fun ArticleListScreenPreview() {
    KecemaasanScreen()
}