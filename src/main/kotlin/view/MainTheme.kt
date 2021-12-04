package view

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MainTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content, colors = colors)
}

private val colors = Colors(
    primary = resource.Colors.NAVY,
    primaryVariant = resource.Colors.NAVY,
    background = Color.White,
    surface = Color.White,
    error = Color.Red,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.Black,
    secondary = resource.Colors.NAVY,
    secondaryVariant = resource.Colors.NAVY,
    onSecondary = Color.White,
    isLight = true,
)
