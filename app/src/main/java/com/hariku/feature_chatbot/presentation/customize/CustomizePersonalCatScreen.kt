package com.hariku.feature_chatbot.presentation.customize

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun CustomizePersonalCatScreen(navController: NavController) {
    var languageStyle by remember { mutableFloatStateOf(0.5f) }
    var professionalStyle by remember { mutableFloatStateOf(0.5f) }
    var feedbackType by remember { mutableStateOf("CBT-Based") }
    var selectedGoals by remember { mutableStateOf(setOf("Refleksi Diri")) }
    var otherGoal by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CustomizeTopBar(
                onBackClick = { navController.popBackStack() }
            )
        },
        containerColor = Color(0xFFF2F2F2)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            LanguageStyleSection(
                languageStyle = languageStyle,
                onLanguageStyleChange = { languageStyle = it },
                professionalStyle = professionalStyle,
                onProfessionalStyleChange = { professionalStyle = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            FeedbackTypeSection(
                feedbackType = feedbackType,
                onFeedbackTypeChange = { feedbackType = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            GoalSection(
                selectedGoals = selectedGoals,
                onGoalsChange = { selectedGoals = it },
                otherGoal = otherGoal,
                onOtherGoalChange = { otherGoal = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB88157)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Selesai",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
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
            rememberNavController()
        )
    }
}
