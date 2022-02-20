package view.components.templates

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable

@Composable
fun LoadingTemplate(content: @Composable () -> Unit) {
    Box {
        content()
    }
}
