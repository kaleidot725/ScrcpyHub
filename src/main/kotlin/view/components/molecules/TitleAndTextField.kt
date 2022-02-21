package view.components.molecules

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import view.components.atoms.Texts

@Composable
fun TitleAndTextField(
    subtitle1: String,
    subtitle2: String,
    inputText: String,
    onUpdateInputText: (String) -> Unit,
    error: String = "",
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Texts.Subtitle1(subtitle1)
            Texts.Subtitle2(subtitle2)
            TextField(
                value = inputText,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onUpdateInputText(it) },
                maxLines = 1
            )

            if (error.isNotEmpty()) {
                Texts.Caption(error)
            }
        }
    }
}

@Preview
@Composable
private fun DeviceNameSetting_Preview() {
    TitleAndTextField(
        subtitle1 = "SUBTITLE1",
        subtitle2 = "SUBTITLE2",
        inputText = "INPUT TEXT",
        onUpdateInputText = {},
        modifier = Modifier
    )
}