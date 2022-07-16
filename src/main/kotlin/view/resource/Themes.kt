package view

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import view.resource.Colors.md_theme_dark_background
import view.resource.Colors.md_theme_dark_error
import view.resource.Colors.md_theme_dark_onBackground
import view.resource.Colors.md_theme_dark_onError
import view.resource.Colors.md_theme_dark_onPrimary
import view.resource.Colors.md_theme_dark_onSecondary
import view.resource.Colors.md_theme_dark_onSurface
import view.resource.Colors.md_theme_dark_primary
import view.resource.Colors.md_theme_dark_secondary
import view.resource.Colors.md_theme_dark_surface
import view.resource.Colors.md_theme_light_background
import view.resource.Colors.md_theme_light_error
import view.resource.Colors.md_theme_light_onBackground
import view.resource.Colors.md_theme_light_onError
import view.resource.Colors.md_theme_light_onPrimary
import view.resource.Colors.md_theme_light_onSecondary
import view.resource.Colors.md_theme_light_onSurface
import view.resource.Colors.md_theme_light_primary
import view.resource.Colors.md_theme_light_secondary
import view.resource.Colors.md_theme_light_surface

@Composable
fun MainTheme(isDarkMode: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        content = content,
        colors = if (isDarkMode) darkThemeColors else lightThemeColors
    )
}

private val lightThemeColors = lightColors(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
)

private val darkThemeColors = darkColors(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
)
