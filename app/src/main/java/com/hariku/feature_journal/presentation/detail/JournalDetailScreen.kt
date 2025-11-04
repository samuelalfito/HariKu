package com.hariku.feature_journal.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hariku.R
import com.hariku.feature_journal.domain.model.JournalData
import com.hariku.feature_journal.presentation.components.AppTopBar
import com.hariku.feature_journal.presentation.components.JournalCard
import com.hariku.feature_journal.presentation.components.JournalListCard
import kotlinx.coroutines.launch

@Composable
fun JournalDetailScreen() {
    val tabTitles = listOf("List", "Tampilan Buku")
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope()
    val journalDatas = listOf(
        JournalData(1, "Burnout Kuliah", "24 / 4 / 2023", "Isi jurnal tentang burnout kuliah..."),
        JournalData(2, "Hutang banyak", "25 / 4 / 2023"),
        JournalData(3, "Pacar selingkuh", "26 / 4 / 2023"),
        JournalData(4, "Gakpunya duit", "27 / 4 / 2023"),
        JournalData(5, "Ditolak magang", "28 / 4 / 2023"),
    )
    
    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        topBar = {
            AppTopBar(
                title = "Stress",
                onBackClick = { /* TODO */ }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(2.dp, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        val selected = pagerState.currentPage == index
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    coroutineScope.launch { pagerState.scrollToPage(index) }
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (selected) Color(0xffc97d50) else Color.Transparent
                            )
                        ) {
                            Text(
                                text = title,
                                color = if (selected) Color.White else Color(0xFF9E9E9E),
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                            )
                        }
                    }
                }
            }
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) { page ->
                when (page) {
                    0 -> ListViewContent(datas = journalDatas)
                    1 -> BookViewContent(data = journalDatas[0])
                }
            }
        }
    }
}

@Composable
fun ListViewContent(datas: List<JournalData>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(datas) { data ->
            JournalListCard(data)
        }
    }
}

@Composable
fun BookViewContent(data: JournalData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JournalCard(
            title = "STRES",
            bgRes = R.drawable.img_pink_bg
        )
        
        JournalListCard(
            data = data,
            showDesc = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun JournalDetailScreenListPreview() {
    JournalDetailScreen()
}