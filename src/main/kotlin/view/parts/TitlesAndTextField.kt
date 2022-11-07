package view.parts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TitlesAndTextField(
    subtitle1: String,
    subtitle2: String,
    inputText: String,
    onUpdateInputText: (String) -> Unit,
    error: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
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

@Preview
@Composable
private fun TitlesAndTextField_Preview() {
    TitlesAndTextField(
        subtitle1 = "SUBTITLE1",
        subtitle2 = "SUBTITLE2",
        inputText = "INPUT TEXT",
        onUpdateInputText = {},
        modifier = Modifier
    )
}
