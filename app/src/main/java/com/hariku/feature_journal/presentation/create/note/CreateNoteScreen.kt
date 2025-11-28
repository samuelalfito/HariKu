package com.hariku.feature_journal.presentation.create.note

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hariku.R
import com.hariku.core.ui.components.CustomizeTopBar
import com.hariku.feature_journal.domain.model.Journal
import com.hariku.feature_journal.presentation.JournalViewModel
import com.hariku.feature_journal.presentation.components.ConfirmationDialog
import com.hariku.feature_journal.presentation.components.SuccessDialog
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    navController: NavController,
    prefillTitle: String? = null,
    viewModel: CreateNoteViewModel = koinViewModel(),
    journalViewModel: JournalViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val journalState by journalViewModel.uiState.collectAsState()
    
    var showBackConfirmDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showJournalPicker by remember { mutableStateOf(false) }
    
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.forLanguageTag("id-ID")) }
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.forLanguageTag("id-ID")) }
    
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let { viewModel.addImage(it) }
    }

    LaunchedEffect(viewModel.saveSuccess) {
        if (viewModel.saveSuccess) {
            showSuccessDialog = true
        }
    }
    LaunchedEffect(prefillTitle) {
        if (!prefillTitle.isNullOrBlank()) {
            viewModel.prefillTitle(prefillTitle)
        }
    }
    Scaffold(
        containerColor = Color(0xfff2f2f2),
        topBar = {
            CustomizeTopBar(
                title = "Buat Catatan",
                onBackClick = { showBackConfirmDialog = true }
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showJournalPicker = true }
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_cat_handing_book),
                                contentDescription = "Cat Journal",
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .size(90.dp)
                                    .offset(x = 8.dp, y = 8.dp)
                            )
                            Column(
                                Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 110.dp, top = 32.dp)
                            ) {
                                Text(
                                    text = viewModel.selectedJournalTitle.ifEmpty { "Pilih Jurnal" },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = if (viewModel.selectedJournalTitle.isEmpty())
                                        Color(0xFFB1B1B1)
                                    else Color(0xFF222222)
                                )
                                Spacer(Modifier.height(8.dp))
                                Card(
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(2.dp),
                                    onClick = {
                                        val calendar = Calendar.getInstance()
                                        calendar.timeInMillis = viewModel.selectedDateTime
                                        
                                        DatePickerDialog(
                                            context,
                                            { _, year, month, day ->
                                                calendar.set(year, month, day)
                                                TimePickerDialog(
                                                    context,
                                                    { _, hour, minute ->
                                                        calendar.set(Calendar.HOUR_OF_DAY, hour)
                                                        calendar.set(Calendar.MINUTE, minute)
                                                        viewModel.updateDateTime(calendar.timeInMillis)
                                                    },
                                                    calendar.get(Calendar.HOUR_OF_DAY),
                                                    calendar.get(Calendar.MINUTE),
                                                    true
                                                ).show()
                                            },
                                            calendar.get(Calendar.YEAR),
                                            calendar.get(Calendar.MONTH),
                                            calendar.get(Calendar.DAY_OF_MONTH)
                                        ).show()
                                    }
                                ) {
                                    Text(
                                        text = "${dateFormat.format(Date(viewModel.selectedDateTime))}, ${
                                            timeFormat.format(
                                                Date(viewModel.selectedDateTime)
                                            )
                                        } WIB",
                                        fontSize = 13.sp,
                                        color = Color(0xFF222222),
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp,
                                            vertical = 2.dp
                                        )
                                    )
                                }
                            }
                        }
                    }
                    
                    Spacer(Modifier.height(18.dp))
                    
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(Modifier.padding(20.dp)) {
                            // Judul
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Judul Catatan",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    color = Color(0xFF222222),
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "${viewModel.title.length}/60",
                                    fontSize = 13.sp,
                                    color = Color(0xFFB1B1B1)
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = viewModel.title,
                                onValueChange = { viewModel.updateTitle(it) },
                                placeholder = {
                                    Text(
                                        "Isi judul catatanmu",
                                        color = Color(0xFFB1B1B1)
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFD88C5A),
                                    unfocusedBorderColor = Color(0xFFE5E5E5)
                                )
                            )
                            
                            Spacer(Modifier.height(16.dp))
                            
                            // Tambah Gambar
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Tambah Gambar",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    color = Color(0xFF222222),
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "${viewModel.imageUris.size}/3",
                                    fontSize = 13.sp,
                                    color = Color(0xFFB1B1B1)
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            HorizontalDivider(color = Color(0xFFE5E5E5), thickness = 1.dp)
                            Spacer(Modifier.height(12.dp))
                            
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                if (viewModel.imageUris.size < 3) {
                                    item {
                                        Box(
                                            Modifier
                                                .size(90.dp, 110.dp)
                                                .border(
                                                    1.dp,
                                                    Color(0xFFB1B1B1),
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .clickable {
                                                    galleryLauncher.launch(
                                                        PickVisualMediaRequest(
                                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                                        )
                                                    )
                                                }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_add),
                                                contentDescription = "Add Image",
                                                tint = Color(0xFFB1B1B1),
                                                modifier = Modifier.align(Alignment.Center)
                                            )
                                        }
                                    }
                                }
                                
                                items(viewModel.imageUris) { uri ->
                                    Box(
                                        Modifier.size(90.dp, 110.dp)
                                    ) {
                                        AsyncImage(
                                            model = uri,
                                            contentDescription = "Selected Image",
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(12.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                        IconButton(
                                            onClick = { viewModel.removeImage(uri) },
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .size(24.dp)
                                                .offset(x = 4.dp, y = (-4).dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "Remove",
                                                tint = Color.White,
                                                modifier = Modifier
                                                    .background(
                                                        Color.Black.copy(alpha = 0.5f),
                                                        RoundedCornerShape(12.dp)
                                                    )
                                                    .padding(4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            
                            Spacer(Modifier.height(16.dp))
                            
                            // Teks
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Teks",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    color = Color(0xFF222222),
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "${viewModel.content.length}/50000",
                                    fontSize = 13.sp,
                                    color = Color(0xFFB1B1B1)
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            HorizontalDivider(color = Color(0xFFE5E5E5), thickness = 1.dp)
                            Spacer(Modifier.height(12.dp))
                            OutlinedTextField(
                                value = viewModel.content,
                                onValueChange = { viewModel.updateContent(it) },
                                placeholder = {
                                    Text(
                                        "Isi catatanmu di sini",
                                        color = Color(0xFFB1B1B1)
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFFD88C5A),
                                    unfocusedBorderColor = Color(0xFFE5E5E5)
                                )
                            )
                            
                            Spacer(Modifier.height(24.dp))
                            
                            Button(
                                onClick = { viewModel.saveNote() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFD88C5A),
                                    disabledContainerColor = Color(0xFFE5E5E5)
                                ),
                                enabled = viewModel.canSave()
                            ) {
                                if (viewModel.isSaving) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else {
                                    Text(
                                        "Selesai",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Dialogs
            if (showBackConfirmDialog) {
                ConfirmationDialog(
                    title = "Yakin ingin kembali?",
                    description = "Perubahan Anda tidak akan tersimpan",
                    confirmText = "Ya",
                    cancelText = "Tidak",
                    onConfirm = {
                        showBackConfirmDialog = false
                        viewModel.resetState()
                        navController.navigate("home?tabIndex=2") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onDismiss = { showBackConfirmDialog = false }
                )
            }
            
            if (showSuccessDialog) {
                SuccessDialog(
                    title = "Terima Kasih!",
                    description = "Catatanmu telah tersimpan, semoga harimu menyenangkan!",
                    buttonText = "Kembali ke Menu Utama",
                    onDismiss = {
                        showSuccessDialog = false
                        viewModel.resetState()
                        navController.navigate("home?tabIndex=2") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }
            
            if (showJournalPicker) {
                JournalPickerDialog(
                    journals = journalState.journals,
                    onJournalSelected = { journal ->
                        viewModel.selectJournal(journal.id, journal.title)
                        showJournalPicker = false
                    },
                    onDismiss = { showJournalPicker = false }
                )
            }
        }
    }
}

@Composable
fun JournalPickerDialog(
    journals: List<Journal>,
    onJournalSelected: (Journal) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Pilih Jurnal",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                if (journals.isEmpty()) {
                    Text(
                        text = "Belum ada jurnal. Buat jurnal terlebih dahulu.",
                        fontSize = 14.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.heightIn(max = 400.dp)
                    ) {
                        journals.forEach { journal ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF6F6F6)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onJournalSelected(journal) }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = journal.title,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp,
                                            color = Color(0xFF222222)
                                        )
                                        Text(
                                            text = journal.date,
                                            fontSize = 12.sp,
                                            color = Color(0xFF666666)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFD88C5A)
                    )
                ) {
                    Text("Batal", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}