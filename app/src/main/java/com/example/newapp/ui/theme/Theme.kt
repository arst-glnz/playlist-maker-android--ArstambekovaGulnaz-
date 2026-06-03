package com.example.newapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val YandexBlue = Color(0xFF3772E7)

private val DarkColorScheme = darkColorScheme(
    primary = YandexBlue,
    background = Color(0xFF1A1B22),
    surface = Color(0xFF1A1B22),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2F3036),
    onSurfaceVariant = Color(0xFF818C99),
)

private val LightColorScheme = lightColorScheme(
    primary = YandexBlue,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE6E8EB),
    onSurfaceVariant = Color(0xFF818C99),
)

@Composable
fun NewAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
