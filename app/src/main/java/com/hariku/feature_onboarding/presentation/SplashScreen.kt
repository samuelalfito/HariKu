package com.hariku.feature_onboarding.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hariku.R
import com.hariku.core.ui.components.Routes
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    // 1. Ganti 'onTimeout' dengan NavController dan ViewModel
    navController: NavController,
    viewModel: SplashScreenViewModel = koinViewModel()
) {
    // 2. Ambil NavState dari ViewModel
    val navState by viewModel.navState.collectAsState()

    // 3. Hapus LaunchedEffect(Unit) { delay(2500) ... } yang lama
    //    Kita ganti dengan yang di bawah (poin 5)

    // 4. UI Anda (Box, Text, Image) tetap sama persis.
    //    Ini akan ditampilkan selama statusnya 'LOADING'
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFCFC))
    ) {
        Text(
            text = "HariKu",
            style = TextStyle(
                color = Color(0xFFC97D50),
                fontSize = 78.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-100).dp)
        )

        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Splash Cat",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }

    // 5. Tambahkan LaunchedEffect BARU yang bereaksi terhadap 'navState'
    LaunchedEffect(navState) {
        // Blok ini akan berjalan setiap kali 'navState' berubah
        when (navState) {
            AuthNavigationState.AUTHENTICATED -> {
                // Arahkan ke Home dan hapus splash dari back stack
                navController.navigate(Routes.PIN_GRAPH) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
            AuthNavigationState.UNAUTHENTICATED -> {
                // Arahkan ke Login dan hapus splash dari back stack
                navController.navigate(Routes.ONBOARDING) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
            AuthNavigationState.LOADING -> {
                // Saat status masih 'LOADING', kita tidak melakukan apa-apa.
                // UI di poin 4 akan tetap ditampilkan.
            }
        }
    }
}