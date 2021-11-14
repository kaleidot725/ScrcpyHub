package view.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import resource.Images

@Composable
fun AppWindow(onCloseRequest: () -> Unit, state: WindowState, content: @Composable FrameWindowScope.() -> Unit) {
    Window(
        onCloseRequest = onCloseRequest,
        state = state,
        resizable = false,
        undecorated = true,
        transparent = true,
        icon = painterResource(Images.DEVICE)
    ) {
        Card(shape = RoundedCornerShape(8.dp)) { content.invoke(this) }
    }
}
