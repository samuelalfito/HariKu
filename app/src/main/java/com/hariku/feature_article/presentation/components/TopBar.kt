package com.hariku.feature_article.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.hariku.R
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.Orange80

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(end = 28.dp)
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .align(Alignment.CenterStart)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Orange80
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
            .background(AdaptiveColors.adaptiveBackground())
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
                tint = AdaptiveColors.adaptiveTextSecondary()
            )

            Spacer(modifier = Modifier.width(6.dp))

            BasicTextField(
                value = query,
                onValueChange = { onQueryChange(it) },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = AdaptiveColors.adaptiveText()
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                cursorBrush = SolidColor(AdaptiveColors.adaptiveText()),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (query.isEmpty()) {
                            Text(
                                text = "Cari Artikel",
                                style = MaterialTheme.typography.bodySmall,
                                color = AdaptiveColors.adaptiveTextSecondary()
                            )
                        }
                        innerTextField()
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { }
                )
            )

            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Clear",
                        modifier = Modifier.size(18.dp),
                        tint = AdaptiveColors.adaptiveTextSecondary()
                    )
                }
            }
        }
    }
}
