package view.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import view.components.atoms.Texts

@Composable
fun TitleAndActionButton(
    subtitle1: String,
    subtitle2: String,
    actionButtonText: String,
    actionButtonColors: ButtonColors,
    onClickActionButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Texts.Subtitle1(text = subtitle1, maxLines = 1)
        Texts.Subtitle2(text = subtitle2, maxLines = 1)
        Button(
            onClick = onClickActionButton,
            colors = actionButtonColors,
            modifier = Modifier.wrapContentHeight().width(80.dp)
        ) {
            Texts.Button(actionButtonText)
        }
    }
}