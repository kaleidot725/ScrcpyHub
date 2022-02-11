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
import resource.Images
import resource.Strings
import view.MainContent
import view.components.AppWindow
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

    Tray(state = trayState, icon = painterResource(Images.TRAY), menu = {
        Item(Strings.TRAY_TOGGLE_SCRCPY_HUB, onClick = { isOpen = !isOpen })

        Item(Strings.TRAY_VERSION, enabled = false, onClick = {})

        Separator()

        Item(Strings.QUIT, onClick = {
            exitApplication()
        })
    })

    if (isOpen) {
        AppWindow(onCloseRequest = { isOpen = false }, state = windowState) {
            MainContent(windowScope = this, mainContentViewModel = viewModel)
        }
    }
}
