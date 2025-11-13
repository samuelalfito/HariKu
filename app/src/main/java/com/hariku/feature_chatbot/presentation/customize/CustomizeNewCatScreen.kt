package com.hariku.feature_chatbot.presentation.customize

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hariku.R
import com.hariku.core.ui.theme.HariKuTheme
import com.hariku.feature_chatbot.presentation.components.CustomizeTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizeNewCatScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var catName by remember { mutableStateOf("") }
    var selectedAvatarIndex by remember { mutableStateOf(0) }

    val avatarList = listOf(
        R.drawable.ic_customize_cat_01,
        R.drawable.ic_customize_cat_02,
        R.drawable.ic_customize_cat_03,
        R.drawable.ic_customize_cat_04,
        R.drawable.ic_customize_cat_05,
        R.drawable.ic_customize_cat_06,
        R.drawable.ic_customize_cat_07,
        R.drawable.ic_customize_cat_08,
        R.drawable.ic_customize_cat_09,
        R.drawable.ic_customize_cat_10,
        R.drawable.ic_customize_cat_11,
        R.drawable.ic_customize_cat_12
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
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nama Karakter",
                fontSize = 16.sp,
                color = Color(0xFF9E9E9E),
            )

            OutlinedTextField(
                value = catName,
                onValueChange = { catName = it },
                placeholder = {
                    Text(
                        text = "Masukkan nama karakter",
                        fontSize = 16.sp,
                        color = Color(0xFFBDBDBD)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFC87C47),
                    unfocusedBorderColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Pilih Avatar",
                fontSize = 16.sp,
                color = Color(0xFF9E9E9E),
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(avatarList.size) { index ->
                        AvatarSelectionItem(
                            avatarDrawable = avatarList[index],
                            isSelected = selectedAvatarIndex == index,
                            onClick = { selectedAvatarIndex = index }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC87C47)
                ),
                enabled = catName.isNotBlank()
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
fun AvatarSelectionItem(
    avatarDrawable: Int,
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
            painter = painterResource(id = avatarDrawable),
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
fun CustomizeNewCatScreenPreview() {
    HariKuTheme {
        CustomizeNewCatScreen(rememberNavController())
    }
}

