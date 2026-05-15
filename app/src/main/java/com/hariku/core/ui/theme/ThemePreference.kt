package com.hariku.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Enum untuk menentukan tema aplikasi
 */
enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM  // Mengikuti system preference
}

/**
 * Data class untuk menyimpan theme state
 */
data class ThemeState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val isDarkTheme: Boolean = false,
    val isDynamicColorEnabled: Boolean = true
)

/**
 * CompositionLocal untuk menyimpan theme state
 */
val LocalThemeState = compositionLocalOf { ThemeState() }
val LocalThemeController = compositionLocalOf<ThemeController?> { null }

/**
 * Interface untuk mengontrol theme
 */
interface ThemeController {
    fun setThemeMode(mode: ThemeMode)
    fun toggleDarkMode()
    fun setDynamicColor(enabled: Boolean)
    fun getCurrentThemeMode(): ThemeMode
    fun isDarkModeEnabled(): Boolean
}

/**
 * Implementation default dari ThemeController
 */
class DefaultThemeController : ThemeController {
    private val _themeMode = mutableStateOf(ThemeMode.SYSTEM)
    private val _dynamicColorEnabled = mutableStateOf(true)

    override fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    override fun toggleDarkMode() {
        _themeMode.value = when (_themeMode.value) {
            ThemeMode.DARK -> ThemeMode.LIGHT
            else -> ThemeMode.DARK
        }
    }

    override fun setDynamicColor(enabled: Boolean) {
        _dynamicColorEnabled.value = enabled
    }

    override fun getCurrentThemeMode(): ThemeMode = _themeMode.value

    override fun isDarkModeEnabled(): Boolean = _themeMode.value == ThemeMode.DARK
}

/**
 * Helper function untuk menentukan apakah dark mode aktif
 */
@Composable
fun shouldUseDarkTheme(themeMode: ThemeMode): Boolean {
    return when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
    }
}

