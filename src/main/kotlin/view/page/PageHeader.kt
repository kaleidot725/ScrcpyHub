package view.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope

@Composable
fun PageHeader(windowScope: WindowScope, title: String, optionContent: @Composable () -> Unit) {
    windowScope.WindowDraggableArea {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(bottom = 8.dp)
                .background(Color(red = 51, blue = 51, green = 51))
                .padding(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = Color.White,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(fraction = 0.95f)
                    .align(Alignment.CenterVertically),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Box(modifier = Modifier.align(Alignment.CenterVertically)) { optionContent() }
        }
    }
}