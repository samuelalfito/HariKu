package com.hariku.core.ui.components

import ConfirmPinScreen
import PinScreenFull
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hariku.feature_auth.presentation.login.LoginScreen
import com.hariku.feature_auth.presentation.register.RegisterScreen
import com.hariku.feature_chatbot.presentation.detail.ChatDetailScreen
import com.hariku.feature_onboarding.presentation.Onboarding1Screen
import com.hariku.feature_onboarding.presentation.Onboarding2Screen
import com.hariku.feature_onboarding.presentation.Onboarding3Screen
import com.hariku.feature_onboarding.presentation.SplashScreen
import com.hariku.feature_pin.presentation.FillPinScreen
import com.hariku.feature_profile.presentation.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.SPLASH){

        composable(Routes.SPLASH){
            SplashScreen() {
                navController.navigate(Routes.ONBOARDING1){
                    popUpTo(Routes.SPLASH){
                        inclusive = true
                    }
                }
            }
        }
        composable(Routes.ONBOARDING1){
            Onboarding1Screen(navController = navController)
        }
        composable(Routes.ONBOARDING2){
            Onboarding2Screen(navController = navController)
        }
        composable(Routes.ONBOARDING3){
            Onboarding3Screen(navController = navController)
        }

        navigation(
            startDestination = Routes.LOGIN,
            route = Routes.AUTH_GRAPH
        ){
            composable(Routes.LOGIN){
                LoginScreen(navController = navController)
            }
            composable(Routes.REGISTER){
                RegisterScreen(navController = navController)
            }
        }

        navigation(
            startDestination = Routes.TETAPKAN_PIN,
            route = Routes.PIN_GRAPH
        ){
            composable(Routes.TETAPKAN_PIN){
                PinScreenFull(navController = navController)
            }
            composable(Routes.KONFIRMASI_PIN){
                ConfirmPinScreen(navController = navController)
            }
            composable(Routes.MASUKKAN_PIN){
                FillPinScreen(navController = navController)
            }
        }

        composable(Routes.HOME) {
            MainScaffold(parentNavController = navController)
        }
        
        composable(Routes.PROFILE) {
            ProfileScreen(navController = navController)
        }

        composable(Routes.DETAIL_CHATBOT_PLACEHOLDER){
            ChatDetailScreen(navController = navController)
        }
    }
}