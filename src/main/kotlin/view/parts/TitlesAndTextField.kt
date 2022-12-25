package view.parts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldAndError(
    label: String,
    placeHolder: String,
    inputText: String,
    onUpdateInputText: (String) -> Unit,
    error: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        TextField(
            value = inputText,
            onValueChange = { onUpdateInputText(it) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            label = { Text(label) },
            placeholder = { Text(placeHolder) },
            isError = error.isNotEmpty()
        )

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@Preview
@Composable
private fun TextFieldAndError_Preview() {
    TextFieldAndError(
        label = "LABEL",
        placeHolder = "PLACEHOLDER",
        inputText = "INPUT TEXT",
        onUpdateInputText = {},
        modifier = Modifier
    )
}

@Preview
@Composable
private fun TextFieldAndError_HAS_ERROR_Preview() {
    TextFieldAndError(
        label = "LABEL",
        placeHolder = "PLACEHOLDER",
        inputText = "INPUT TEXT",
        error = "ERROR MEASSAGE",
        onUpdateInputText = {},
        modifier = Modifier
    )
}

