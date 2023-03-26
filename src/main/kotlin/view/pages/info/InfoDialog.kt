package view.pages.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import view.resource.Images
import view.resource.MainTheme
import view.resource.Strings
import view.resource.Strings.INFO_TITLE

@Composable
fun InfoDialog(isDark: Boolean, onClose: () -> Unit) {
    MainTheme(isDarkMode = isDark) {
        Dialog(
            onCloseRequest = onClose,
            title = INFO_TITLE,
            icon = painterResource(Images.TRAY),
        ) {
            Surface {
                InfoContent(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun InfoContent(modifier: Modifier = Modifier) {
    Box(modifier) {
        Row(Modifier.wrapContentSize().align(Alignment.Center)) {
            Box(modifier = Modifier.height(100.dp)) {
                Image(
                    bitmap = useResource("icon.png") { loadImageBitmap(it) },
                    contentDescription = "icon",
                    modifier = Modifier.size(44.dp).align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(modifier = Modifier.height(100.dp)) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = Strings.APP_NAME,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Version ${Strings.VERSION}",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}
