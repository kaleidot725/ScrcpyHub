import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import model.di.appModule
import org.koin.core.context.GlobalContext.startKoin
import view.MainContent

fun main() = application {
    val trayState = rememberTrayState()
    var isOpen by remember { mutableStateOf(true) }
    var windowState = rememberWindowState(width = 350.dp, height = 550.dp)

    try {
        startKoin {
            printLogger()
            modules(appModule)
        }
    } catch (e: Exception) {
    }

    Tray(
        state = trayState,
        icon = TrayIcon,
        menu = {
            Item(
                "Open",
                onClick = {
                    isOpen = true
                }
            )

            Item(
                "Close",
                onClick = {
                    isOpen = false
                }
            )

            Item(
                "Exit",
                onClick = {
                    exitApplication()
                }
            )
        }
    )

    if (isOpen) {
        Window(
            title = "HOME",
            onCloseRequest = { isOpen = false },
            resizable = false,
            state = windowState
        ) {
            MainContent()
        }
    }
}

object TrayIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        drawOval(Color(0xFFFFA500))
    }
}