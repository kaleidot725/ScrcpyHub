import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.rememberWindowState
import model.di.appModule
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.getOrNull
import org.koin.core.context.GlobalContext.startKoin
import view.AppWindow
import view.MainContent
import view.resource.Images
import view.resource.Strings
import viewmodel.MainContentViewModel

fun main() = application {
    if (getOrNull() == null) {
        startKoin {
            modules(appModule)
        }
    }

    val trayState = rememberTrayState()
    val windowState = rememberWindowState(width = 350.dp, height = 550.dp)
    val viewModel by remember { mutableStateOf(GlobalContext.get().get<MainContentViewModel>()) }
    var isOpen by remember { mutableStateOf(true) }
    var alwaysOnTop by remember { mutableStateOf(false) }

    Tray(state = trayState, icon = painterResource(Images.TRAY), menu = {
        CheckboxItem(
            text = Strings.TRAY_SHOW_SCRCPY_HUB,
            checked = isOpen,
            onCheckedChange = { isOpen = it }
        )

        CheckboxItem(
            text = Strings.TRAY_ENABLE_ALWAYS_TOP,
            checked = alwaysOnTop,
            onCheckedChange = { alwaysOnTop = it }
        )

        Separator()

        Item(Strings.QUIT, onClick = {
            exitApplication()
        })
    })

    if (isOpen) {
        AppWindow(onCloseRequest = { isOpen = false }, state = windowState, alwaysOnTop = alwaysOnTop) {
        MainContent(windowScope = this, mainContentViewModel = viewModel)
    }
    }
}
