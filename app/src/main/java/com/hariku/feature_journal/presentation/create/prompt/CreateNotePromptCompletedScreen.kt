package com.hariku.feature_journal.presentation.create.prompt

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import com.hariku.R
import com.hariku.core.ui.components.CustomizeTopBar
import com.hariku.core.ui.components.Routes
import com.hariku.feature_journal.presentation.components.PromptItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateNotePromptCompletedScreen(
    navController: NavController,
    viewModel: CreateNotePromptCompletedViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState

    LaunchedEffect(Unit) {
        // Clear if user revisits screen so stale data isn’t reused
        viewModel.clearSelection()
    }

    Scaffold(
        containerColor = Color(0xFFF6F6F6),
        topBar = {
            CustomizeTopBar(
                title = "Buat Prompt AI",
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 24.dp, start = 16.dp, end = 16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        viewModel.clearSelection()
                        navController.navigate(Routes.CreateNote.route)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                    enabled = !uiState.isLoading,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(2.dp, Color(0xFFD88C5A)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Text(
                        "Lewati",
                        color = Color(0xFFD88C5A),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFD88C5A))
                }
            } else if (uiState.promptResponse != null) {
                val response = uiState.promptResponse!!
                
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF82b3bb)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Row(
                        Modifier.fillMaxSize()
                    ) {
                        Box(
                            Modifier
                                .weight(1f)
                                .padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
                        ) {
                            Text(
                                "Terima kasih telah membagi perasaanmu! ",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            )
                        }
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_light_source_1),
                                contentDescription = "Arrow Right",
                                modifier = Modifier.fillMaxHeight()
                            )
                            Image(
                                painter = painterResource(id = R.drawable.img_cat_head),
                                contentDescription = "AI Prompt Icon",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(top = 12.dp)
                                    .offset(x = 20.dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.img_cat_hand),
                                contentDescription = "AI Prompt Icon",
                                modifier = Modifier
                                    .padding(top = 25.dp)
                                    .offset(x = (-40).dp, y = 4.dp)
                            )
                        }
                    }
                }
                
                Spacer(Modifier.height(20.dp))
                
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = response.introduction,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color(0xFF222222)
                        )
                        
                        Spacer(Modifier.height(12.dp))
                        
                        LazyColumn(
                            modifier = Modifier.weight(1f, fill = false),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(response.suggestions) { suggestion ->
                                PromptItem(
                                    text = suggestion.text,
                                    isSelected = suggestion.isSelected,
                                    onClick = {
                                        viewModel.selectPrompt(suggestion.id)
                                        val selectedTitle = viewModel.selectedPromptTitle()
                                        val route = if (!selectedTitle.isNullOrBlank()) {
                                            Routes.CreateNote.withPrefillTitle(selectedTitle)
                                        } else {
                                            Routes.CreateNote.route
                                        }
                                        navController.navigate(route)
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Tidak ada prompt yang tersedia",
                        color = Color(0xFF9E9E9E),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CreateNotePromptCompletedScreen(NavController(LocalContext.current))
}