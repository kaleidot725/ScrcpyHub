package view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import view.resource.Colors
import view.resource.Images
import view.resource.Strings

@Composable
fun MainWindow(
    onCloseRequest: () -> Unit,
    state: WindowState,
    alwaysOnTop: Boolean,
    content: @Composable FrameWindowScope.() -> Unit
) {
    Window(
        onCloseRequest = onCloseRequest,
        state = state,
        title = Strings.APP_NAME,
        resizable = false,
        undecorated = true,
        transparent = true,
        alwaysOnTop = alwaysOnTop,
        icon = painterResource(Images.DEVICE),
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Colors.WINDOW_BORDER)
        ) { content.invoke(this) }
    }
}
