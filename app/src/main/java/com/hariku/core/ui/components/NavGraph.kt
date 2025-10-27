package com.hariku.core.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hariku.feature_auth.presentation.login.LoginScreen
import com.hariku.feature_auth.presentation.register.RegisterScreen

object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val PROFILE = "profile"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.LOGIN){
        composable(Destinations.LOGIN){
            LoginScreen()
        }
        composable(Destinations.REGISTER){
            RegisterScreen()
        }
    }
}