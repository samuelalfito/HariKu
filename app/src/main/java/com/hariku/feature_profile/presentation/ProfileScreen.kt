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
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.hariku.R
import com.hariku.core.ui.components.Routes
import org.koin.androidx.compose.koinViewModel
import com.hariku.core.ui.theme.AdaptiveColors
import com.hariku.core.ui.theme.Coral30
import com.hariku.core.ui.theme.Coral60
import com.hariku.core.ui.theme.Neutral100
import com.hariku.core.ui.theme.SpecialPeachText
import com.hariku.core.ui.theme.LocalThemeController
import com.hariku.core.ui.theme.LocalThemeState
import com.hariku.core.ui.theme.ThemeMode

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenViewModel = koinViewModel()
) {
    val themeState = LocalThemeState.current
    val themeController = LocalThemeController.current

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(AdaptiveColors.adaptiveBackground()),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack(route = Routes.Home.route, inclusive = false)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = "Back Icon",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Text(
                    text = "Profile",
                    color = AdaptiveColors.adaptiveText(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        containerColor = AdaptiveColors.adaptiveBackground()
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
                colors = CardDefaults.cardColors(containerColor = AdaptiveColors.adaptiveCardBackground()),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = viewModel.currentUser?.photoUrl,
                        contentDescription = "Foto Profil",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        placeholder = ColorPainter(SpecialPeachText),
                        error = ColorPainter(SpecialPeachText)
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
                            color = AdaptiveColors.adaptiveText()
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
                        color = AdaptiveColors.adaptiveTextSecondary()
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
                isToggle = true,
                toggleChecked = themeState.isDarkTheme,
                onToggleChange = { checked ->
                    themeController?.setThemeMode(
                        if (checked) ThemeMode.DARK else ThemeMode.LIGHT
                    )
                }
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
                textColor = Coral60,
                disableRipple = true,
                onClick = {
                    viewModel.onLogoutClicked()

                    navController.navigate(Routes.AuthGraph.route) {
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
    textColor: Color = AdaptiveColors.adaptiveText(),
    isToggle: Boolean = false,
    toggleChecked: Boolean = false,
    onToggleChange: (Boolean) -> Unit = {},
    disableRipple: Boolean = false,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AdaptiveColors.adaptiveCardBackground()),
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
                    checked = toggleChecked,
                    onCheckedChange = onToggleChange,
                    modifier = Modifier.size(36.dp),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Neutral100,
                        checkedTrackColor = Coral30,
                        uncheckedThumbColor = Neutral100,
                        uncheckedTrackColor = AdaptiveColors.adaptiveDivider()
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
