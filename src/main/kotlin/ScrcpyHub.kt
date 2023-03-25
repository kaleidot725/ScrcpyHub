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
    val windowState = rememberWindowState(width = 350.dp, height = 550.dp)
    var isOpen by remember { mutableStateOf(true) }
    var showLicense by remember { mutableStateOf(false) }
    var alwaysOnTop by remember { mutableStateOf(false) }

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

            Separator()

            CheckboxItem(
                text = Strings.TRAY_ABOUT_LICENSE,
                checked = showLicense,
                onCheckedChange = { showLicense = it }
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

        MainWindow(
            onCloseRequest = { isOpen = false },
            state = windowState,
            alwaysOnTop = alwaysOnTop
        ) {
            MainContent(
                windowScope = this,
                mainStateHolder = stateHolder
            )
        }

        if (showLicense) {
            val isDarkMode: Boolean? by stateHolder.isDarkMode.collectAsState(null)
            LicenseDialog(
                isDark = isDarkMode ?: false,
                onClose = { showLicense = false },
            )
        }
    }
}
