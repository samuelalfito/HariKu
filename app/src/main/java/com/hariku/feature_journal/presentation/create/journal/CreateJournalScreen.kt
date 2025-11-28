package com.hariku.feature_journal.presentation.create.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.hariku.feature_journal.domain.model.StickerElement
import com.hariku.feature_journal.domain.model.TextElement
import com.hariku.feature_journal.presentation.components.BackgroundTab
import com.hariku.feature_journal.presentation.components.DraggableSticker
import com.hariku.feature_journal.presentation.components.EditableText
import com.hariku.feature_journal.presentation.components.StickerTab
import com.hariku.feature_journal.presentation.components.TabButton
import com.hariku.feature_journal.presentation.components.TextTab
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJournalScreen(
    navController: NavController,
    viewModel: CreateJournalViewModel = koinViewModel(),
) {
    val notebookBackground by viewModel.notebookBackground.collectAsState()
    val textElements by viewModel.textElements.collectAsState()
    val stickerElements by viewModel.stickerElements.collectAsState()
    val selectedTextIndex by viewModel.selectedTextIndex.collectAsState()
    val selectedStickerIndex by viewModel.selectedStickerIndex.collectAsState()
    
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Buat Jurnal",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFFCF6D49)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.saveJournal()
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save",
                            tint = Color(0xFFCF6D49)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5))
        ) {
            // Notebook Canvas Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        viewModel.setSelectedTextIndex(null)
                        viewModel.setSelectedStickerIndex(null)
                    },
                contentAlignment = Alignment.Center
            ) {
                NotebookCanvas(
                    backgroundColor = notebookBackground,
                    textElements = textElements,
                    stickerElements = stickerElements,
                    selectedTextIndex = selectedTextIndex,
                    selectedStickerIndex = selectedStickerIndex,
                    onTextElementMove = { index, offset ->
                        val updated = textElements.toMutableList()
                        updated[index] = updated[index].copy(offsetX = offset.x, offsetY = offset.y)
                        viewModel.setTextElements(updated)
                    },
                    onTextChange = { index, newText ->
                        val updated = textElements.toMutableList()
                        updated[index] = updated[index].copy(text = newText)
                        viewModel.setTextElements(updated)
                    },
                    onTextScaleChange = { index, scale ->
                        val updated = textElements.toMutableList()
                        updated[index] = updated[index].copy(scale = scale)
                        viewModel.setTextElements(updated)
                    },
                    onStickerElementMove = { index, offset ->
                        val updated = stickerElements.toMutableList()
                        updated[index] = updated[index].copy(offsetX = offset.x, offsetY = offset.y)
                        viewModel.setStickerElements(updated)
                    },
                    onStickerScaleChange = { index, scale ->
                        val updated = stickerElements.toMutableList()
                        updated[index] = updated[index].copy(scale = scale)
                        viewModel.setStickerElements(updated)
                    },
                    onStickerRotationChange = { index, rotation ->
                        val updated = stickerElements.toMutableList()
                        updated[index] = updated[index].copy(rotation = rotation)
                        viewModel.setStickerElements(updated)
                    },
                    onTextClick = { index ->
                        viewModel.setSelectedTextIndex(index)
                        viewModel.setSelectedStickerIndex(null)
                        viewModel.setSelectedTab(BottomTab.TEXT)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    onStickerClick = { index ->
                        viewModel.setSelectedStickerIndex(index)
                        viewModel.setSelectedTextIndex(null)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    },
                    onTextDelete = { index ->
                        val updated = textElements.toMutableList()
                        updated.removeAt(index)
                        viewModel.setTextElements(updated)
                        viewModel.setSelectedTextIndex(null)
                    },
                    onStickerDelete = { index ->
                        val updated = stickerElements.toMutableList()
                        updated.removeAt(index)
                        viewModel.setStickerElements(updated)
                        viewModel.setSelectedStickerIndex(null)
                    }
                )
            }
            
            // Tab Selection
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TabButton(
                    text = "Teks",
                    isSelected = pagerState.currentPage == 0,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                        viewModel.setSelectedTab(BottomTab.TEXT)
                    }
                )
                TabButton(
                    text = "Latar",
                    isSelected = pagerState.currentPage == 1,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                        viewModel.setSelectedTab(BottomTab.BACKGROUND)
                    }
                )
                TabButton(
                    text = "Stiker",
                    isSelected = pagerState.currentPage == 2,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                        viewModel.setSelectedTab(BottomTab.STICKER)
                    }
                )
            }
            
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                HorizontalPager(
                    count = 3,
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> TextTab(
                            selectedTextElement = selectedTextIndex?.let { index ->
                                textElements.getOrNull(index)
                            },
                            onAddText = {
                                val newText = TextElement(
                                    id = System.currentTimeMillis(),
                                    text = "Teks",
                                    offsetX = 100f,
                                    offsetY = 100f
                                )
                                val newList = textElements + newText
                                viewModel.setTextElements(newList)
                                viewModel.setSelectedTextIndex(newList.size - 1)
                            },
                            onUpdateTextProperty = { updater ->
                                selectedTextIndex?.let { index ->
                                    val updated = textElements.toMutableList()
                                    updated[index] = updater(updated[index])
                                    viewModel.setTextElements(updated)
                                }
                            }
                        )
                        
                        1 -> BackgroundTab(
                            selectedColor = notebookBackground,
                            onColorSelected = { viewModel.setNotebookBackground(it) }
                        )
                        
                        2 -> StickerTab(
                            onStickerSelected = { sticker ->
                                val newSticker = StickerElement(
                                    id = System.currentTimeMillis(),
                                    emoji = sticker,
                                    offsetX = 100f,
                                    offsetY = 100f
                                )
                                viewModel.setStickerElements(stickerElements + newSticker)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotebookCanvas(
    backgroundColor: Color,
    textElements: List<TextElement>,
    stickerElements: List<StickerElement>,
    selectedTextIndex: Int?,
    selectedStickerIndex: Int?,
    onTextElementMove: (Int, Offset) -> Unit,
    onTextChange: (Int, String) -> Unit,
    onTextScaleChange: (Int, Float) -> Unit,
    onStickerElementMove: (Int, Offset) -> Unit,
    onStickerScaleChange: (Int, Float) -> Unit,
    onStickerRotationChange: (Int, Float) -> Unit,
    onTextClick: (Int) -> Unit,
    onStickerClick: (Int) -> Unit,
    onTextDelete: (Int) -> Unit,
    onStickerDelete: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .shadow(8.dp, RoundedCornerShape(16.dp))
    ) {
        // Spiral binding
        Column(
            modifier = Modifier
                .width(40.dp)
                .fillMaxHeight()
                .zIndex(2f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(11) {
                Box(
                    modifier = Modifier
                        .size(width = 36.dp, height = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF757575))
                )
            }
        }
        
        // Notebook background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(backgroundColor)
        ) {
            // Text elements
            textElements.forEachIndexed { index, element ->
                EditableText(
                    textElement = element,
                    isSelected = selectedTextIndex == index,
                    onDrag = { offset ->
                        onTextElementMove(index, offset)
                    },
                    onClick = { onTextClick(index) },
                    onDelete = { onTextDelete(index) },
                    onTextChange = { newText ->
                        onTextChange(index, newText)
                    },
                    onScaleChange = { scale ->
                        onTextScaleChange(index, scale)
                    }
                )
            }
            
            // Sticker elements
            stickerElements.forEachIndexed { index, element ->
                DraggableSticker(
                    stickerElement = element,
                    isSelected = selectedStickerIndex == index,
                    onDrag = { offset ->
                        onStickerElementMove(index, offset)
                    },
                    onClick = { onStickerClick(index) },
                    onDelete = { onStickerDelete(index) },
                    onScaleChange = { scale ->
                        onStickerScaleChange(index, scale)
                    },
                    onRotationChange = { rotation ->
                        onStickerRotationChange(index, rotation)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun CreateJournalScreenPreview() {
    CreateJournalScreen(navController = NavController(LocalContext.current))
}