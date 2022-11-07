package view.parts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TitleAndActionButton(
    subtitle1: String,
    subtitle2: String,
    actionButtonText: String,
    actionButtonColors: ButtonColors,
    onClickActionButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.align(Alignment.CenterStart).fillMaxWidth().padding(end = 80.dp)) {
            Texts.Subtitle1(text = subtitle1, maxLines = 1)
            Texts.Subtitle2(text = subtitle2, maxLines = 1)
        }

        Button(
            onClick = onClickActionButton,
            colors = actionButtonColors,
            modifier = Modifier.wrapContentHeight().width(80.dp).align(Alignment.CenterEnd)
        ) {
            Texts.Button(actionButtonText)
        }
    }
}

@Preview
@Composable
private fun TitleAndActionButton_Preview() {
    TitleAndActionButton(
        subtitle1 = "CUSTOM SUBTITLE1",
        subtitle2 = "CUSTOM SUBTITLE2",
        actionButtonText = "RUN",
        actionButtonColors = ButtonDefaults.buttonColors(),
        onClickActionButton = {},
        modifier = Modifier.fillMaxWidth()
    )
}
