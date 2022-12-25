package view.pages.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.WindowScope
import model.entity.Theme
import view.components.AppSetting
import view.parts.SubPageHeader
import view.resource.Strings
import view.templates.HeaderAndContent

@Composable
fun SettingPage(
    windowScope: WindowScope,
    stateHolder: SettingPageStateHolder,
    onNavigateDevices: (() -> Unit)? = null,
    onSaved: (() -> Unit)? = null
) {
    val theme: Theme by stateHolder.theme.collectAsState()
    val themes: List<Theme> by stateHolder.themes.collectAsState()
    val adbLocation: String by stateHolder.adbLocation.collectAsState()
    val scrcpyLocation: String by stateHolder.scrcpyLocation.collectAsState()

    DisposableEffect(stateHolder) {
        stateHolder.onStarted()
        onDispose {
            stateHolder.onCleared()
        }
    }

    HeaderAndContent(
        header = {
            SubPageHeader(
                windowScope = windowScope,
                title = Strings.SETTING_PAGE_TITLE,
                onBack = { onNavigateDevices?.invoke() },
            )
        },
        content = {
            AppSetting(
                theme = theme,
                themes = themes,
                onUpdateTheme = { stateHolder.updateTheme(it) },
                adbLocation = adbLocation,
                onUpdateAdbLocation = { stateHolder.updateAdbLocation(it) },
                scrcpyLocation = scrcpyLocation,
                onUpdateScrcpyLocation = { stateHolder.updateScrcpyLocation(it) },
                onSave = {
                    stateHolder.save { onSaved?.invoke() }
                    onNavigateDevices?.invoke()
                }
            )
        }
    )
}
