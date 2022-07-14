package view.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import model.entity.Theme
import view.components.organisms.AppSetting
import view.resource.Images
import view.resource.Strings
import view.tab.PageHeader
import view.templates.HeaderAndContent
import view.pages.SettingPageStateHolder

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
            PageHeader(
                windowScope = windowScope,
                title = Strings.SETTING_PAGE_TITLE,
                optionContent = {
                    Image(
                        painter = painterResource(Images.CLOSE),
                        contentDescription = "",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(18.dp)
                            .clickable { onNavigateDevices?.invoke() }
                    )
                }
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
                onSave = { stateHolder.save { onSaved?.invoke() } }
            )
        }
    )
}
