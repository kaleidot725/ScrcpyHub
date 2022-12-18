package view.parts

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Start
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TitleAndIcon(
    title: String,
    iconVector: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.align(Alignment.Center).padding(4.dp)
        ) {
            Texts.Subtitle2(
                text = title,
                maxLines = 1,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

//            Icon(
//                imageVector = iconVector,
//                contentDescription = title,
//                modifier = Modifier.size(16.dp).align(Alignment.CenterVertically)
//            )
        }
    }

}

@Preview
@Composable
private fun TitleAndIcon_Preview() {
    TitleAndIcon(
        title = "Start",
        iconVector = Icons.Default.Start,
        modifier = Modifier.fillMaxWidth()
    )
}
