package com.sahilm.tutorly.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

/**
 * Default Material3 Typography for Tutorly app
 * You can customize this further based on your design system
 */
private val DefaultTypography = Typography()

/**
 * Tutorly theme with light and dark mode support
 *
 * @param darkTheme whether to use dark theme colors
 * @param content the composable content
 */
@Composable
fun TutorlyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DefaultTypography,
        content = content
    )
}

/**
 * Helper function to detect if the system is in dark theme
 * This checks the system's dark mode setting
 */
@Composable
private fun isSystemInDarkTheme(): Boolean {
    return androidx.compose.foundation.isSystemInDarkTheme()
}


