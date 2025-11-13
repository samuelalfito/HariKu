package com.hariku.feature_journal.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hariku.R
import com.hariku.feature_journal.domain.model.Journal
import com.hariku.feature_journal.presentation.components.JournalCard
import com.hariku.feature_journal.presentation.components.SearchBar
import androidx.navigation.NavController
import com.hariku.core.ui.components.Routes

@Composable
fun JournalScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val journals = listOf(
        Journal("STRES", R.drawable.img_pink_bg),
        Journal("HARIAN", R.drawable.img_green_bg),
        Journal("Blunder", R.drawable.img_purple_bg)
    )
    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFc87c47)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Journal",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jurnal",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A7A)),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    Text("SOS", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            SearchBar(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = "Cari Jurnal atau Halaman",
            )
            Spacer(modifier = Modifier.height(32.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(journals.size) { index ->
                    val journal = journals[index]
                    JournalCard(
                        title = journal.title,
                        bgRes = journal.bgRes,
                        onClick = {
                            navController.navigate(Routes.JournalDetail.createRoute(journal.title))
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    JournalScreen(navController = NavController(LocalContext.current))
}