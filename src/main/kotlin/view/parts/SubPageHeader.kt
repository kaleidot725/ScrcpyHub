package view.parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import view.resource.Strings

@Composable
fun SubPageHeader(
    windowScope: WindowScope,
    title: String,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    savable: Boolean,
) {
    windowScope.WindowDraggableArea {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(red = 51, blue = 51, green = 51))
                .padding(8.dp)
        ) {
            TextButton(onClick = onCancel) {
                Text(
                    text = Strings.CANCEL,
                    color = Color.White
                )
            }

            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1.0f)
                    .align(Alignment.CenterVertically),
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )

            TextButton(onClick = onSave, enabled = savable) {
                Text(
                    text = Strings.SAVE,
                    color = Color.White
                )
            }
        }
    }
}
