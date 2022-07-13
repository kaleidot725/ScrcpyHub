package view.templates

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HeaderAndContent(header: @Composable () -> Unit, content: @Composable () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            header()
            content()
        }
    }
}
