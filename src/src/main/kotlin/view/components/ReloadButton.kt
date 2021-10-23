package view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import resource.Colors
import resource.Images

@Composable
fun RefreshButton(onReload: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Card(
            shape = CircleShape,
            backgroundColor = Colors.NAVY,
            modifier = Modifier.size(24.dp).clickable { onReload() }.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(Images.REFRESH),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun RefreshButton_Preview() {
    RefreshButton({})
}