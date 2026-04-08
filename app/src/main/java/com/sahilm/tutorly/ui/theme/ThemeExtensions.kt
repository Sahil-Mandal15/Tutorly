package com.sahilm.tutorly.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Suppress("unused")
/**
 * Extension functions to access theme colors throughout the app
 * Usage: val color = TutorlyTheme.colors.primary
 */
object TutorlyColors {
    /**
     * Get the current color scheme based on the theme
     */
    val MaterialColors: androidx.compose.material3.ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme
}

/**
 * Semantic color extensions for common use cases
 */
object SemanticColors {
    /**
     * Success color - typically green
     */
    val success: androidx.compose.ui.graphics.Color
        @Composable
        get() = if (androidx.compose.foundation.isSystemInDarkTheme()) DarkSuccess else LightSuccess

    /**
     * Warning color - typically yellow/orange
     */
    val warning: androidx.compose.ui.graphics.Color
        @Composable
        get() = if (androidx.compose.foundation.isSystemInDarkTheme()) DarkWarning else LightWarning

    /**
     * Error color
     */
    val error: androidx.compose.ui.graphics.Color
        @Composable
        get() = if (androidx.compose.foundation.isSystemInDarkTheme()) DarkError else LightError

    /**
     * Info color - typically blue
     */
    val info: androidx.compose.ui.graphics.Color
        @Composable
        get() = if (androidx.compose.foundation.isSystemInDarkTheme()) DarkInfo else LightInfo
}


