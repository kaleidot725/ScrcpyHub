package view.parts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldAndError(
    label: String,
    placeHolder: String,
    inputText: String,
    onUpdateInputText: (String) -> Unit,
    error: String = "",
    trailingIcon: ImageVector? = null,
    onClickTrailingIcon: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TextField(
            value = inputText,
            onValueChange = { onUpdateInputText(it) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            label = { Text(label) },
            placeholder = { Text(placeHolder) },
            isError = error.isNotEmpty(),
            trailingIcon =
                trailingIcon?.let {
                    {
                        Image(
                            imageVector = trailingIcon,
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
                            contentDescription = null,
                            modifier =
                                Modifier
                                    .clip(CircleShape)
                                    .indication(MutableInteractionSource(), rememberRipple())
                                    .clickable(onClick = { onClickTrailingIcon() })
                                    .padding(4.dp),
                        )
                    }
                },
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
        modifier = Modifier,
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
        modifier = Modifier,
    )
}
