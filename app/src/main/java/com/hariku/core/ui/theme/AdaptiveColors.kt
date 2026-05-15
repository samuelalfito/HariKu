package com.hariku.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Adaptive colors yang berubah berdasarkan theme (light/dark)
 * Gunakan composable ini untuk mendapatkan warna yang sesuai dengan theme saat ini
 */
object AdaptiveColors {
    
    /**
     * Helper untuk mengecek apakah sedang dalam dark mode berdasarkan ThemeState
     */
    @Composable
    private fun isDark(): Boolean {
        return LocalThemeState.current.isDarkTheme
    }

    /**
     * Mendapatkan background color yang sesuai dengan theme
     */
    @Composable
    fun adaptiveBackground(): Color {
        return if (isDark()) {
            DarkModeBgPrimary
        } else {
            BgLight
        }
    }

    /**
     * Mendapatkan text color yang sesuai dengan theme
     */
    @Composable
    fun adaptiveText(): Color {
        return if (isDark()) {
            DarkModeTextPrimary
        } else {
            TextDark
        }
    }

    /**
     * Mendapatkan secondary text color
     */
    @Composable
    fun adaptiveTextSecondary(): Color {
        return if (isDark()) {
            DarkModeTextSecondary
        } else {
            Neutral50
        }
    }

    /**
     * Mendapatkan card/surface background
     */
    @Composable
    fun adaptiveCardBackground(): Color {
        return if (isDark()) {
            DarkModeCardBg
        } else {
            Neutral100
        }
    }

    /**
     * Mendapatkan divider color
     */
    @Composable
    fun adaptiveDivider(): Color {
        return if (isDark()) {
            DarkModeDivider
        } else {
            DividerColor
        }
    }

    /**
     * Mendapatkan border color
     */
    @Composable
    fun adaptiveBorder(): Color {
        return if (isDark()) {
            Neutral30
        } else {
            BorderColor
        }
    }

    /**
     * Mendapatkan primary button color (usually sama di light & dark)
     */
    @Composable
    fun adaptivePrimaryButton(): Color {
        return Orange70  // Consistent across themes
    }

    /**
     * Mendapatkan warna untuk disabled state
     */
    @Composable
    fun adaptiveDisabled(): Color {
        return if (isDark()) {
            Neutral30
        } else {
            Neutral85
        }
    }

    /**
     * Mendapatkan surface tint color dari Material Theme
     */
    @Composable
    fun surfaceTint(): Color {
        return MaterialTheme.colorScheme.surfaceTint
    }
}

/**
 * Extension functions untuk Color yang membantu dalam dark mode
 */

/**
 * Mendapatkan warna yang sesuai berdasarkan isDark flag
 */
fun Color.adaptToTheme(lightColor: Color, darkColor: Color, isDark: Boolean): Color {
    return if (isDark) darkColor else lightColor
}

/**
 * Mendapatkan warna dengan opacity yang disesuaikan untuk dark mode
 */
@Composable
fun Color.adaptiveWithAlpha(
    lightAlpha: Float = 1f,
    darkAlpha: Float = 0.87f
): Color {
    val isDark = LocalThemeState.current.isDarkTheme
    val alpha = if (isDark) darkAlpha else lightAlpha
    return this.copy(alpha = alpha)
}
