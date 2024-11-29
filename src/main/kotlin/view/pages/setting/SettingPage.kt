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
import view.templates.MainLayout

@Composable
fun SettingPage(
    windowScope: WindowScope,
    stateHolder: SettingPageStateHolder,
    onNavigateDevices: (() -> Unit)? = null,
    onSaved: (() -> Unit)? = null,
) {
    val theme: Theme by stateHolder.theme.collectAsState()
    val themes: List<Theme> by stateHolder.themes.collectAsState()
    val adbLocation: String by stateHolder.adbLocation.collectAsState()
    val scrcpyLocation: String by stateHolder.scrcpyLocation.collectAsState()
    val screenshotDirectory: String by stateHolder.screenshotDirectory.collectAsState()
    val screenRecordDirectory: String by stateHolder.screenRecordDirectory.collectAsState()

    DisposableEffect(stateHolder) {
        stateHolder.onStarted()
        onDispose {
            stateHolder.onCleared()
        }
    }

    MainLayout(
        header = {
            SubPageHeader(
                windowScope = windowScope,
                title = Strings.SETTING_PAGE_TITLE,
                onCancel = { onNavigateDevices?.invoke() },
                onSave = {
                    stateHolder.save { onSaved?.invoke() }
                    onNavigateDevices?.invoke()
                },
                savable = true,
            )
        },
        content = {
            AppSetting(
                theme = theme,
                themes = themes,
                onUpdateTheme = stateHolder::updateTheme,
                adbLocation = adbLocation,
                onUpdateAdbLocation = stateHolder::updateAdbLocation,
                scrcpyLocation = scrcpyLocation,
                onUpdateScrcpyLocation = stateHolder::updateScrcpyLocation,
                screenRecordDirectory = screenRecordDirectory,
                onUpdateScreenshotDirectory = stateHolder::updateScreenshotDirectory,
                screenshotDirectory = screenshotDirectory,
                onUpdateScreenRecordDirectory = stateHolder::updateScreenRecordDirectory,
            )
        },
    )
}
