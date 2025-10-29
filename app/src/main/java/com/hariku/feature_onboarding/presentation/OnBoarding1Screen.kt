package com.hariku.feature_onboarding.presentation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.R
import com.hariku.core.ui.components.Routes

@Composable
fun Onboarding1Screen(navController: NavController) {

    val boxColorSelected = Color(0xFFC97D50)
    val boxColorUnselected  = Color(0xFFDDDDDD)
    val onboardingImages = listOf(
        R.drawable.onboarding1,
        R.drawable.onboarding2,
        R.drawable.onboarding3
    )
    val onboardingImagesPadding = listOf(
        110.dp,
        0.dp,
        0.dp
    )
    val titles = listOf(
        "Halo, Aku HariKu!",
        "Kenali Dirimu Lebih Baik",
        "Mari Kita Mulai!"
    )
    val texts = listOf(
        "Aku adalah kucing menggemaskan yang siap membantumu melewati masa sulit. Kamu bisa curahkan isi hatimu ke aku dengan aman",
        "Kamu bisa bertanya kepadaku tentang tips menjaga kesehatan mental. Selain itu, terdapat artikel dan aktivitas lain yang bisa membantumu!",
        "Kenali lebih dalam kesehatan mentalmu dengan tanggapan dan rekap chat Calico!"
    )

    /*ANIMATON STUFFS RRRAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHH*/
    var selectedBoxIndex by remember { mutableStateOf(1) }

    val transitionBox1 = updateTransition(targetState = selectedBoxIndex,label = "box1")
    val transitionBox2 = updateTransition(targetState = selectedBoxIndex,label = "box2")
    val transitionBox3 = updateTransition(targetState = selectedBoxIndex,label = "box3")

    val box1Width by transitionBox1.animateDp(label = "box1Width") { state ->
        when(state){
            1 -> 20.dp
            else -> 6.dp
        }
    }
    val box1Color by transitionBox1.animateColor(label = "box1Color") { state ->
        when(state){
            1 -> boxColorSelected
            else -> boxColorUnselected
        }
    }

    val box2Width by transitionBox2.animateDp(label = "box1Width") { state ->
        when(state){
            2 -> 20.dp
            else -> 6.dp
        }
    }
    val box2Color by transitionBox1.animateColor(label = "box2Color") { state ->
        when(state){
            2 -> boxColorSelected
            else -> boxColorUnselected
        }
    }

    val box3Width by transitionBox3.animateDp(label = "box1Width") { state ->
        when(state){
            3 -> 20.dp
            else -> 6.dp
        }
    }
    val box3Color by transitionBox1.animateColor(label = "box3Color") { state ->
        when(state){
            3 -> boxColorSelected
            else -> boxColorUnselected
        }
    }
    /*ANIMATON STUFFS RRRAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHH*/

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFEBF5FB),
                        Color(0xFF8FC5F6)
                    )
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = onboardingImages[selectedBoxIndex-1]),
            contentDescription = "Onboarding 1",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = onboardingImagesPadding[selectedBoxIndex-1])
                .align(Alignment.TopCenter)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = titles[selectedBoxIndex-1],
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp,
                        color = Color(0xFF1E1E1E)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = texts[selectedBoxIndex-1],
                        fontSize = 18.sp,
                        color = Color(0xFF757575),
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = box1Width, height = 6.dp)
                                .clip(CircleShape)
                                .background(box1Color)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(width = box2Width, height = 6.dp)
                                .clip(CircleShape)
                                .background(box2Color)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(width = box3Width, height = 6.dp)
                                .clip(CircleShape)
                                .background(box3Color)
                        )
                    }

                    Spacer(modifier = Modifier.height(64.dp))

                    Button(
                        onClick = { /*TODO: Animate progress dots*/
                            if(selectedBoxIndex == 3){
                                navController.navigate(Routes.AUTH_GRAPH)
                            }else{
                                selectedBoxIndex = ((selectedBoxIndex + 1)%4)
                                if(selectedBoxIndex==0){selectedBoxIndex+=1}
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC97D50)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Lanjut", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Lewati",
                        color = Color(0xFFC97D50),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Routes.AUTH_GRAPH)
                            }
                    )

                    Spacer(modifier = Modifier.height(56.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Onboarding1ScreenPreview() {
    Onboarding1Screen(rememberNavController())
}