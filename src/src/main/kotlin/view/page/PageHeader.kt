package view.tab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PageHeader(title: String, icon: Painter, onAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(bottom = 8.dp)
            .background(Color.Black)
            .padding(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(fraction = 0.95f)
                .align(Alignment.CenterVertically)
        )
        Image(
            painter = icon,
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .wrapContentWidth()
                .height(18.dp)
                .align(Alignment.CenterVertically)
                .clickable { onAction() }
        )
    }
}