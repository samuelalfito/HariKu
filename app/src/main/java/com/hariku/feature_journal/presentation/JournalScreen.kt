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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.hariku.feature_journal.presentation.components.JournalPreviewData
import com.hariku.feature_journal.presentation.components.SearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun JournalScreen(
    navController: NavController,
//    viewModel: JournalViewModel = koinViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
//    val uiState by viewModel.uiState.collectAsState()
    
//    val journals = remember(uiState.journals) {
//        uiState.journals.map { entry ->
//            Journal(
//                title = entry.title,
//                bgRes = entry.bgRes
//            )
//        }
//    }
    val journals = listOf(
        JournalPreviewData(),
        JournalPreviewData(backgroundColor = Color.Red)
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
                    onClick = { navController.navigate(Routes.SosGraph.route) },
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

            if (/*uiState.isLoading*/false) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            color = Color(0xFFFF8A7A)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Memuat jurnal...", color = Color.Gray)
                    }
                }
            }
            else if (/*uiState.error != null*/false) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "⚠️",
                            fontSize = 48.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = /*uiState.error ?:*/ "Terjadi kesalahan",
                            color = Color.Red,
                            fontSize = 16.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { /*viewModel.loadJournals()*/ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A7A))
                        ) {
                            Text("Coba Lagi", color = Color.White)
                        }
                    }
                }
            }
            else if (journals.isEmpty() && searchQuery.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "Belum ada jurnal",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Buat jurnal pertamamu dengan menekan tombol + di bawah",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
            else if (journals.none { it.textElements.any { it.text.contains(searchQuery, ignoreCase = true)  } }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "Tidak ditemukan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tidak ada jurnal dengan kata kunci \"$searchQuery\"",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
            else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(journals.filter { it.textElements.any { it.text.contains(searchQuery, ignoreCase = true) } }) { journal ->

                        JournalCard(
                            backgroundColor = journal.backgroundColor,
                            textElements = journal.textElements,
                            stickerElements = journal.stickerElements
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