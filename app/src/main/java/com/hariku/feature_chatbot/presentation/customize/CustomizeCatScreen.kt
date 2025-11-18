package com.hariku.feature_chatbot.presentation.customize

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.hariku.core.ui.theme.HariKuTheme
import com.hariku.feature_chatbot.presentation.components.CustomizeTopBar
import com.hariku.core.ui.components.Routes

data class CatData(
    val id: Int,
    val drawable: Int,
    val name: String,
    val subtitle: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizeCatScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var selectedCatIndex by remember { mutableIntStateOf(0) }

    val catDataList = listOf(
        CatData(
            id = 1,
            drawable = R.drawable.ic_customize_cat_01,
            name = "HariKu",
            subtitle = "EMPATI DAN SUPORTIF",
            description = "Hariku adalah kucing yang menggunakan terapi perilaku kognitif (CBT) dan memiliki kepribadian yang ramah dan suportif, siap untuk mendengarkan ceritamu!"
        ),
        CatData(
            id = 2,
            drawable = R.drawable.ic_customize_cat_02,
            name = "Luna",
            subtitle = "CERIA DAN OPTIMIS",
            description = "Luna adalah kucing yang penuh energi positif, selalu siap menghibur dan membuat harimu lebih cerah dengan pendekatan yang menyenangkan!"
        ),
        CatData(
            id = 3,
            drawable = R.drawable.ic_customize_cat_03,
            name = "Moy",
            subtitle = "PROFESIONAL DAN INFORMATIF",
            description = "Moy adalah kucing yang memberikan informasi tentang kondisi kesehatan mental dan menghubungkan mereka dengan sumber daya kesehatan mental."
        ),
        CatData(
            id = 4,
            drawable = R.drawable.ic_customize_cat_04,
            name = "Mochi",
            subtitle = "LEMBUT DAN PENYAYANG",
            description = "Mochi adalah kucing yang sangat lembut dan penuh kasih sayang, selalu memberikan kehangatan dan kenyamanan di setiap percakapan!"
        ),
        CatData(
            id = 5,
            drawable = R.drawable.ic_customize_cat_05,
            name = "Midnight",
            subtitle = "MISTERIUS DAN INTUITIF",
            description = "Midnight adalah kucing yang intuitif dan memahami perasaanmu dengan mendalam, memberikan wawasan yang bermakna untuk pertumbuhanmu!"
        )
    )

    Scaffold(
        topBar = {
            CustomizeTopBar(
                title = "Customize",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = catDataList[selectedCatIndex].drawable),
                        contentDescription = "Selected Cat",
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = catDataList[selectedCatIndex].name,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2F2F30)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = catDataList[selectedCatIndex].subtitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFC87C47),
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = catDataList[selectedCatIndex].description,
                        fontSize = 13.sp,
                        color = Color(0xFF2F2F30),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    for (i in 0..2) {
                        CatSelectionItem(
                            catData = catDataList[i],
                            isSelected = selectedCatIndex == i,
                            onClick = { selectedCatIndex = i }
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    for (i in 3..4) {
                        CatSelectionItem(
                            catData = catDataList[i],
                            isSelected = selectedCatIndex == i,
                            onClick = { selectedCatIndex = i }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                navController.navigate(Routes.CustomizeNewCat.route)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_customize_new_cat),
                            contentDescription = "Add New Cat",
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC87C47)
                )
            ) {
                Text(
                    text = "Lanjut",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CatSelectionItem(
    catData: CatData,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .border(
                width = if (isSelected) 5.dp else 0.dp,
                color = if (isSelected) Color(0xFFB87333) else Color.Transparent,
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = catData.drawable),
            contentDescription = "Cat Option",
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
        )

        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xCCFFFFFF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_checked),
                    contentDescription = "Selected",
                    tint = Color(0xFFC87C47),
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomizeCatScreenPreview() {
    HariKuTheme {
        CustomizeCatScreen(rememberNavController())
    }
}
