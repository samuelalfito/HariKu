package com.hariku.feature_journal.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hariku.R
import com.hariku.core.ui.components.FloatingActButton
import com.hariku.core.ui.components.Routes
import com.hariku.feature_journal.domain.model.Journal
import com.hariku.feature_journal.presentation.components.JournalCard
import com.hariku.feature_journal.presentation.components.SearchBar

@Composable
fun JournalScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val journals = listOf(
        Journal("STRES", R.drawable.img_pink_bg),
        Journal("HARIAN", R.drawable.img_green_bg),
        Journal("Blunder", R.drawable.img_purple_bg)
    )
    val addState = remember { mutableStateOf(false) }
    val fabRotation = if (addState.value) 45f else 0f
    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                if (addState.value) {
                    Column(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .width(150.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 0.dp))
                            .background(Color(0xFFFFD3B0)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                                .clickable {
                                    addState.value = false
                                    navController.navigate(Routes.CreateJournal.route)
                                }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Jurnal Baru", color = Color.Black)
                        }
                        HorizontalDivider()
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                                .clickable {
                                    addState.value = false
                                    navController.navigate(Routes.CreateNote.route)
                                }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Halaman Baru", color = Color.Black)
                        }
                    }
                }
                
                Box(modifier = Modifier.graphicsLayer(rotationZ = fabRotation)) {
                    FloatingActButton(
                        label = "New Journal"
                    ) {
                        addState.value = !addState.value
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
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
                    placeholder = "Cari Jurnal atau Halaman"
                )
                Spacer(modifier = Modifier.height(32.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(journals.filter({it.title.contains(other = searchQuery, ignoreCase = true)})) { journal ->
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
}

@Preview
@Composable
private fun Preview() {
    JournalScreen(navController = NavController(LocalContext.current))
}