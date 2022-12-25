package view.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope

@Composable
fun SubPageHeader(windowScope: WindowScope, title: String, onBack: () -> Unit) {
    windowScope.WindowDraggableArea {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(red = 51, blue = 51, green = 51))
                .padding(8.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(top = 2.dp).align(Alignment.CenterStart)
            ) {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    colorFilter = ColorFilter.tint(Color.White),
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.Center),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }
    }
}