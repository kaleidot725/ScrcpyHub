import androidx.compose.runtime.collectAsState
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
import view.MainContent
import view.MainContentStateHolder
import view.MainWindow
import view.pages.info.InfoDialog
import view.pages.license.LicenseDialog
import view.resource.Images
import view.resource.Strings

fun main() = application {
    if (getOrNull() == null) {
        startKoin {
            modules(appModule)
        }
    }

    val trayState = rememberTrayState()
    var isOpen by remember { mutableStateOf(true) }
    var showLicense by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }
    var alwaysOnTop by remember { mutableStateOf(false) }
    var enableMiniMode by remember { mutableStateOf(false) }

    Tray(
        state = trayState, icon = painterResource(Images.TRAY),
        menu = {
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

            CheckboxItem(
                text = Strings.TRAY_ENABLE_MINI_MODE,
                checked = enableMiniMode,
                onCheckedChange = { enableMiniMode = it }
            )

            Separator()

            CheckboxItem(
                text = Strings.TRAY_ABOUT_LICENSE,
                checked = showLicense,
                onCheckedChange = { showLicense = it }
            )

            CheckboxItem(
                text = Strings.TRAY_ABOUT_SCRCPYHUB,
                checked = showInfo,
                onCheckedChange = { showInfo = it }
            )

            Separator()

            Item(
                Strings.QUIT,
                onClick = { exitApplication() }
            )
        }
    )

    if (isOpen) {
        val stateHolder by remember { mutableStateOf(GlobalContext.get().get<MainContentStateHolder>()) }
        val isDarkMode: Boolean? by stateHolder.isDarkMode.collectAsState(null)

        if (enableMiniMode) {
            val windowState = rememberWindowState(width = 350.dp, height = 160.dp)
            MainWindow(
                onCloseRequest = { isOpen = false },
                state = windowState,
                alwaysOnTop = alwaysOnTop
            ) {
                MainContent(
                    windowScope = this,
                    enableMiniMode = enableMiniMode,
                    mainStateHolder = stateHolder
                )
            }
        } else {
            val windowState = rememberWindowState(width = 350.dp, height = 550.dp)
            MainWindow(
                onCloseRequest = { isOpen = false },
                state = windowState,
                alwaysOnTop = alwaysOnTop
            ) {
                MainContent(
                    windowScope = this,
                    enableMiniMode = enableMiniMode,
                    mainStateHolder = stateHolder
                )
            }
        }

        if (showLicense) {
            LicenseDialog(
                isDark = isDarkMode ?: false,
                onClose = { showLicense = false },
            )
        }

        if (showInfo) {
            InfoDialog(
                isDark = isDarkMode ?: false,
                onClose = { showInfo = false },
            )
        }
    }
}
