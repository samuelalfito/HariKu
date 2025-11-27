package com.hariku.feature_chatbot.presentation.customize

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.core.ui.components.CustomizeTopBar
import com.hariku.feature_chatbot.presentation.components.ThreePointSlider
import com.hariku.feature_chatbot.presentation.components.FeedbackTypeOption
import com.hariku.feature_chatbot.presentation.components.GoalCheckbox
import com.hariku.core.ui.theme.HariKuTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CustomizePersonalCatScreen(
    navController: NavController,
    nama: String,
    avatarResId: Int,
    viewModel: CustomizePersonalCatViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CustomizeTopBar(
                onBackClick = { navController.popBackStack() }
            )
        },
        containerColor = Color(0xFFF2F2F2)
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                LanguageStyleSection(
                    languageStyle = uiState.languageStyle,
                    onLanguageStyleChange = { viewModel.updateLanguageStyle(it) },
                    professionalStyle = uiState.professionalStyle,
                    onProfessionalStyleChange = { viewModel.updateProfessionalStyle(it) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                FeedbackTypeSection(
                    feedbackType = uiState.feedbackType,
                    onFeedbackTypeChange = { viewModel.updateFeedbackType(it) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                GoalSection(
                    selectedGoals = uiState.selectedGoals,
                    onGoalsChange = { viewModel.updateGoals(it) },
                    otherGoal = uiState.otherGoal,
                    onOtherGoalChange = { viewModel.updateOtherGoal(it) }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        viewModel.saveChatbot(
                            name = nama,
                            avatarResId = avatarResId,
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Chatbot berhasil dibuat!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.popBackStack(
                                    route = "customize_cat",
                                    inclusive = false
                                )
                            },
                            onError = { error ->
                                Toast.makeText(
                                    context,
                                    "Error: $error",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB88157)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !uiState.isSaving
                ) {
                    if (uiState.isSaving) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.height(24.dp)
                        )
                    } else {
                        Text(
                            text = "Selesai",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun LanguageStyleSection(
    languageStyle: Float,
    onLanguageStyleChange: (Float) -> Unit,
    professionalStyle: Float,
    onProfessionalStyleChange: (Float) -> Unit
) {
    Text(
        text = "Gaya Bahasa",
        fontSize = 18.sp,
        color = Color(0xFFB0B0B0),
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            ThreePointSlider(
                selectedPoint = languageStyle,
                onPointSelected = onLanguageStyleChange,
                startLabel = "Formal",
                endLabel = "Santai"
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            ThreePointSlider(
                selectedPoint = professionalStyle,
                onPointSelected = onProfessionalStyleChange,
                startLabel = "Profesional",
                endLabel = "Ceria"
            )
        }
    }
}

@Composable
fun FeedbackTypeSection(
    feedbackType: String,
    onFeedbackTypeChange: (String) -> Unit
) {
    Text(
        text = "Tipe Feedback",
        fontSize = 18.sp,
        color = Color(0xFFB0B0B0),
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            FeedbackTypeOption(
                title = "CBT-Based",
                description = "Memberikan petunjuk kepada anda untuk yang menantang pikiran negatif dan mendorong perubahan perilaku positif.",
                isSelected = feedbackType == "CBT-Based",
                onClick = { onFeedbackTypeChange("CBT-Based") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FeedbackTypeOption(
                title = "Goal-Oriented",
                description = "Memberi pengguna petunjuk yang disesuaikan dengan kondisi dan tujuan kesehatan mental mereka.",
                isSelected = feedbackType == "Goal-Oriented",
                onClick = { onFeedbackTypeChange("Goal-Oriented") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FeedbackTypeOption(
                title = "Personalized Conversation",
                description = "Menciptakan pengalaman percakapan yang lebih baik dan memberikan dukungan yang lebih personal kepada pengguna.",
                isSelected = feedbackType == "Personalized Conversation",
                onClick = { onFeedbackTypeChange("Personalized Conversation") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FeedbackTypeOption(
                title = "Scaffolding-based questions",
                description = "Mengajukan pertanyaan berbasis perancah dengan pertanyaan \"mengapa\" atau \"bagaimana\" yang bersifat terbuka.",
                isSelected = feedbackType == "Scaffolding-based questions",
                onClick = { onFeedbackTypeChange("Scaffolding-based questions") }
            )
        }
    }
}

@Composable
fun GoalSection(
    selectedGoals: Set<String>,
    onGoalsChange: (Set<String>) -> Unit,
    otherGoal: String,
    onOtherGoalChange: (String) -> Unit
) {
    Text(
        text = "Tujuan",
        fontSize = 18.sp,
        color = Color(0xFFB0B0B0),
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            GoalCheckbox(
                text = "Refleksi Diri",
                isChecked = selectedGoals.contains("Refleksi Diri"),
                onCheckedChange = {
                    onGoalsChange(
                        if (it) selectedGoals + "Refleksi Diri"
                        else selectedGoals - "Refleksi Diri"
                    )
                }
            )

            GoalCheckbox(
                text = "Mendapat Tips dan Informasi",
                isChecked = selectedGoals.contains("Mendapat Tips dan Informasi"),
                onCheckedChange = {
                    onGoalsChange(
                        if (it) selectedGoals + "Mendapat Tips dan Informasi"
                        else selectedGoals - "Mendapat Tips dan Informasi"
                    )
                }
            )

            GoalCheckbox(
                text = "Mendapatkan Support",
                isChecked = selectedGoals.contains("Mendapatkan Support"),
                onCheckedChange = {
                    onGoalsChange(
                        if (it) selectedGoals + "Mendapatkan Support"
                        else selectedGoals - "Mendapatkan Support"
                    )
                }
            )

            GoalCheckbox(
                text = "Memahami Situasi Lebih Baik",
                isChecked = selectedGoals.contains("Memahami Situasi Lebih Baik"),
                onCheckedChange = {
                    onGoalsChange(
                        if (it) selectedGoals + "Memahami Situasi Lebih Baik"
                        else selectedGoals - "Memahami Situasi Lebih Baik"
                    )
                }
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            OutlinedTextField(
                value = otherGoal,
                onValueChange = onOtherGoalChange,
                placeholder = {
                    Text(
                        text = "Isi Tujuan Lainnya...",
                        color = Color(0xFFB0B0B0),
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF9F9F9),
                    unfocusedContainerColor = Color(0xFFF9F9F9),
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CustomizePersonalCatScreenPreview() {
    HariKuTheme {
        CustomizePersonalCatScreen(
            rememberNavController(),
            nama = "Karakter Saya",
            avatarResId = 0
        )
    }
}
