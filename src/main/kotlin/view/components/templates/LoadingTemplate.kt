package view.components.templates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoadingTemplate(content: @Composable () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box {
            content()
        }
    }
}
