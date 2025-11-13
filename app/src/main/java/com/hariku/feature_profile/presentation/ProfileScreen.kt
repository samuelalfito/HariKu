package com.hariku.feature_profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.hariku.core.ui.components.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenViewModel = koinViewModel() // <-- INJECT VIEWMODEL
) {
    Scaffold(
        topBar = {
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
                        .size(28.dp)
                        .clickable {
                            navController.popBackStack(route = Routes.Home.route, inclusive = false)
                        }
                )
                Text(
                    text = "Profile",
                    color = Color(0xFF242424),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        containerColor = Color(0xFFF2F2F2)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF3B57B))
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = viewModel.currentUser?.name ?: "{NAME NOT SET}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF242424)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Image(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = "Edit",
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Text(
                        text = viewModel.currentUser?.email ?: "{EMAIL NOT SET}",
                        fontSize = 14.sp,
                        color = Color(0xFF6B4E3D)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ProfileMenuItem(
                iconRes = R.drawable.notifikasi,
                text = "Notifikasi"
            )

            ProfileMenuItem(
                iconRes = R.drawable.dark_mode,
                text = "Dark Mode",
                isToggle = true
            )

            ProfileMenuItem(
                iconRes = R.drawable.ketentuan_pengguna,
                text = "Ketentuan Pengguna"
            )

            ProfileMenuItem(
                iconRes = R.drawable.bantuan,
                text = "Bantuan"
            )

            ProfileMenuItem(
                iconRes = R.drawable.keluar,
                text = "Keluar",
                textColor = Color(0xFFD98585),
                disableRipple = true,
                onClick = { // <-- TAMBAHKAN ONCLICK
                    // 1. Panggil ViewModel untuk logout
                    viewModel.onLogoutClicked()

                    // 2. Navigasi kembali ke halaman login
                    // dan hapus semua riwayat navigasi (back stack)
                    navController.navigate(Routes.AuthGraph.route) { // (atau "auth_graph")
                        popUpTo(Routes.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileMenuItem(
    iconRes: Int,
    text: String,
    textColor: Color = Color(0xFF242424),
    isToggle: Boolean = false,
    disableRipple: Boolean = false,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = if (disableRipple) null else LocalIndication.current
            ) {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = text,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = text,
                    fontSize = 16.sp,
                    color = textColor,
                    fontWeight = FontWeight.Medium
                )
            }

            if (isToggle) {
                Switch(
                    checked = false,
                    onCheckedChange = {},
                    modifier = Modifier.size(36.dp),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFFC97D50),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFFE1E1E1)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileScreen() {
    ProfileScreen(rememberNavController())
}
