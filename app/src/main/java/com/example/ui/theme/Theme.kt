package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color(0xFF001F28),
    primaryContainer = Color(0xFF004E5B),
    onPrimaryContainer = Color(0xFFA6EEFF),
    secondary = ElectricViolet,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFF4C1D95),
    onSecondaryContainer = Color(0xFFE9D5FF),
    tertiary = BioEmerald,
    onTertiary = Color(0xFFFFFFFF),
    background = BiotechNavyBg,
    onBackground = Color(0xFFF1F5F9),
    surface = BiotechNavySurface,
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = BiotechGlassCard,
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = BiotechGlassBorder
)

private val LightColorScheme = lightColorScheme(
    primary = LightTealPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBFE9F3),
    onPrimaryContainer = Color(0xFF001F26),
    secondary = LightViolet,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEDE9FE),
    onSecondaryContainer = Color(0xFF2E1065),
    tertiary = BioEmerald,
    onTertiary = Color.White,
    background = LightBg,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,
    outline = Color(0xFFCBD5E1)
)

@Composable
fun GeneLabTheme(
    darkTheme: Boolean = true, // Default to futuristic dark biotechnology theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
