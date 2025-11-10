package com.hariku.core.ui.components

import ChatScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hariku.feature_home.presentation.HomeScreen
import com.hariku.feature_journal.presentation.JournalScreen

@Composable
fun MainScaffold(parentNavController: NavHostController) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val bottomNavRoutes = listOf(
        Routes.HOME,
        Routes.CHATBOT,
        Routes.JOURNAL,
        Routes.STATISTIC
    )
    val selectedIndex = bottomNavRoutes.indexOf(currentRoute).takeIf { it >= 0 } ?: 0
    
    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    val route = bottomNavRoutes[index]
                    if (route != currentRoute) {
                        bottomNavController.navigate(route) {
                            popUpTo(Routes.HOME) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(navController = parentNavController)
            }
            composable(Routes.CHATBOT) {
                ChatScreen(navController = parentNavController)
            }
            composable(Routes.JOURNAL) {
                JournalScreen()
            }
            composable(Routes.STATISTIC) {
                Box {
                    Text("Statistic Screen Coming Soon") /*TODO: Statistic Screen*/
                }
            }
        }
    }
}