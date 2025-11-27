package com.hariku.feature_sense.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hariku.R
import com.hariku.feature_sense.presentation.components.CompletedContent
import com.hariku.feature_sense.presentation.components.StepContent

@Composable
fun SensesScreen(
    navController: NavController,
    viewModel: SensesViewModel = viewModel(),
) {
    val context = LocalContext.current
    
    val animatedProgress by animateFloatAsState(
        targetValue = viewModel.progress,
        animationSpec = tween(durationMillis = 500),
        label = "progressAnimation"
    )
    
    LaunchedEffect(Unit) {
        viewModel.resetToFirstStep()
    }
    
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp, start = 32.dp, end = 32.dp)
            ) {
                Button(
                    onClick = if (viewModel.isCompleted) {
                        { navController.navigateUp() }
                    } else {
                        { viewModel.nextStep() }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC97D50)
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = if (!viewModel.isCompleted) "Lanjut" else "Kembali",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "Back Icon",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .size(34.dp)
                        .clickable { navController.popBackStack() }
                )
                
                Text(
                    text = "Latihan 5 Panca Indra",
                    color = Color(0xFF242424),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            if (viewModel.isCompleted) {
                CompletedContent(
                    progress = animatedProgress
                )
            } else {
                viewModel.currentStep?.let { step ->
                    StepContent(
                        step = step,
                        progress = animatedProgress,
                        progressText = viewModel.progressText,
                        context = context
                    )
                }
            }
        }
    }
}