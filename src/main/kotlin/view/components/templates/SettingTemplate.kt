package view.components.templates

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun SettingTemplate(header: @Composable () -> Unit, content: @Composable () -> Unit) {
    Column {
        header()
        content()
    }
}
