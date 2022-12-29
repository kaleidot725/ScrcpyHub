package view.resource

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import view.resource.Colors.DARK_BACKGROUND
import view.resource.Colors.DARK_ERROR
import view.resource.Colors.DARK_ON_BACKGROUND
import view.resource.Colors.DARK_ON_ERROR
import view.resource.Colors.DARK_ON_PRIMARY
import view.resource.Colors.DARK_ON_SECONDARY
import view.resource.Colors.DARK_ON_SURFACE
import view.resource.Colors.DARK_PRIMARY
import view.resource.Colors.DARK_SECONDARY
import view.resource.Colors.DARK_SURFACE
import view.resource.Colors.LIGHT_BACKGROUND
import view.resource.Colors.LIGHT_ERROR
import view.resource.Colors.LIGHT_ON_BACKGROUND
import view.resource.Colors.LIGHT_ON_ERROR
import view.resource.Colors.LIGHT_ON_PRIMARY
import view.resource.Colors.LIGHT_ON_SECONDARY
import view.resource.Colors.LIGHT_ON_SURFACE
import view.resource.Colors.LIGHT_PRIMARY
import view.resource.Colors.LIGHT_SECONDARY
import view.resource.Colors.LIGHT_SURFACE

@Composable
fun MainTheme(isDarkMode: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        content = content,
        colors = if (isDarkMode) darkThemeColors else lightThemeColors
    )
}

private val lightThemeColors = lightColors(
    primary = LIGHT_PRIMARY,
    onPrimary = LIGHT_ON_PRIMARY,
    secondary = LIGHT_SECONDARY,
    onSecondary = LIGHT_ON_SECONDARY,
    error = LIGHT_ERROR,
    onError = LIGHT_ON_ERROR,
    background = LIGHT_BACKGROUND,
    onBackground = LIGHT_ON_BACKGROUND,
    surface = LIGHT_SURFACE,
    onSurface = LIGHT_ON_SURFACE,
)

private val darkThemeColors = darkColors(
    primary = DARK_PRIMARY,
    onPrimary = DARK_ON_PRIMARY,
    secondary = DARK_SECONDARY,
    onSecondary = DARK_ON_SECONDARY,
    error = DARK_ERROR,
    onError = DARK_ON_ERROR,
    background = DARK_BACKGROUND,
    onBackground = DARK_ON_BACKGROUND,
    surface = DARK_SURFACE,
    onSurface = DARK_ON_SURFACE,
)
