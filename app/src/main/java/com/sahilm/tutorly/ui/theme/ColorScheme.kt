package com.sahilm.tutorly.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

@Suppress("unused")
/**
 * Light mode color scheme based on Material3 guidelines
 */
val LightColorScheme = lightColorScheme(
    // Primary Colors
    primary = Primary,
    onPrimary = White,
    primaryContainer = PrimaryLighter,
    onPrimaryContainer = PrimaryDarker,

    // Secondary Colors
    secondary = Secondary,
    onSecondary = White,
    secondaryContainer = SecondaryLighter,
    onSecondaryContainer = SecondaryDarker,

    // Tertiary Colors
    tertiary = Tertiary,
    onTertiary = White,
    tertiaryContainer = TertiaryLighter,
    onTertiaryContainer = TertiaryDarker,

    // Error Colors
    error = LightError,
    onError = White,
    errorContainer = LightErrorContainer,
    onErrorContainer = LightError,

    // Background & Surface
    background = LightBg,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,

    // Outline
    outline = LightOutline,
    outlineVariant = LightOutlineVariant,
)

/**
 * Dark mode color scheme based on Material3 guidelines
 */
val DarkColorScheme = darkColorScheme(
    // Primary Colors - Lighter variants for dark mode
    primary = PrimaryLight,
    onPrimary = PrimaryDarker,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = PrimaryLighter,

    // Secondary Colors - Lighter variants for dark mode
    secondary = SecondaryLight,
    onSecondary = SecondaryDarker,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = SecondaryLighter,

    // Tertiary Colors - Lighter variants for dark mode
    tertiary = TertiaryLight,
    onTertiary = TertiaryDarker,
    tertiaryContainer = TertiaryDark,
    onTertiaryContainer = TertiaryLighter,

    // Error Colors - Optimized for dark mode
    error = DarkError,
    onError = DarkBg,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkError,

    // Background & Surface - Dark variants
    background = DarkBg,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,

    // Outline
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant,
)



