import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import model.di.appModule
import org.koin.core.context.GlobalContext.getOrNull
import org.koin.core.context.GlobalContext.startKoin
import resource.Images
import resource.Strings.APP_VERSION
import view.MainContent

fun main() = application {
    val trayState = rememberTrayState()
    var isOpen by remember { mutableStateOf(true) }
    val windowState = rememberWindowState(width = 350.dp, height = 550.dp)

    if (getOrNull() == null) {
        startKoin {
            printLogger()
            modules(appModule)
        }
    }

    Tray(
        state = trayState,
        icon = painterResource(Images.TRAY),
        menu = {
            Item(
                "Toggle ScrcpyHub",
                onClick = {
                    isOpen = !isOpen
                }
            )

            Item(
                "ScrcpyHub v${APP_VERSION}",
                enabled = false,
                onClick = {}
            )

            Separator()

            Item(
                "Quit",
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