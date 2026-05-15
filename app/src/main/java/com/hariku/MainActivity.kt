package com.hariku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import com.hariku.core.ui.components.NavGraph
import com.hariku.core.ui.theme.HariKuTheme
import com.hariku.core.ui.theme.LocalThemeController
import com.hariku.core.ui.theme.LocalThemeState
import com.hariku.core.ui.theme.ThemeController
import com.hariku.core.ui.theme.ThemeMode
import com.hariku.core.ui.theme.ThemeState
import com.hariku.core.ui.theme.shouldUseDarkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val themeModeState = rememberSaveable { mutableStateOf(ThemeMode.SYSTEM) }
            val dynamicColorState = rememberSaveable { mutableStateOf(true) }
            val isDark = shouldUseDarkTheme(themeModeState.value)

            val themeController = remember {
                object : ThemeController {
                    override fun setThemeMode(mode: ThemeMode) {
                        themeModeState.value = mode
                    }

                    override fun toggleDarkMode() {
                        themeModeState.value = if (themeModeState.value == ThemeMode.DARK) {
                            ThemeMode.LIGHT
                        } else {
                            ThemeMode.DARK
                        }
                    }

                    override fun setDynamicColor(enabled: Boolean) {
                        dynamicColorState.value = enabled
                    }

                    override fun getCurrentThemeMode(): ThemeMode = themeModeState.value

                    override fun isDarkModeEnabled(): Boolean = isDark
                }
            }

            val themeState = remember(themeModeState.value, isDark, dynamicColorState.value) {
                ThemeState(
                    themeMode = themeModeState.value,
                    isDarkTheme = isDark,
                    isDynamicColorEnabled = dynamicColorState.value
                )
            }

            CompositionLocalProvider(
                LocalThemeState provides themeState,
                LocalThemeController provides themeController
            ) {
                HariKuTheme(
                    darkTheme = isDark,
                    dynamicColor = dynamicColorState.value
                ) {
                    NavGraph(navController = navController)
                }
            }
        }
    }
}