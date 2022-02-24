package view.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import model.entity.Theme
import resource.Images
import resource.Strings
import view.components.organisms.AppSetting
import view.components.templates.SettingTemplate
import view.tab.PageHeader
import viewmodel.SettingPageViewModel

@Composable
fun SettingPage(
    windowScope: WindowScope,
    settingPageViewModel: SettingPageViewModel,
    onNavigateDevices: (() -> Unit)? = null,
    onSaved: (() -> Unit)? = null
) {
    val theme: Theme by settingPageViewModel.theme.collectAsState()
    val themes: List<Theme> by settingPageViewModel.themes.collectAsState()
    val adbLocation: String by settingPageViewModel.adbLocation.collectAsState()
    val scrcpyLocation: String by settingPageViewModel.scrcpyLocation.collectAsState()

    SettingTemplate(
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
                onUpdateTheme = { settingPageViewModel.updateTheme(it) },
                adbLocation = adbLocation,
                onUpdateAdbLocation = { settingPageViewModel.updateAdbLocation(it) },
                scrcpyLocation = scrcpyLocation,
                onUpdateScrcpyLocation = { settingPageViewModel.updateScrcpyLocation(it) },
                onSave = { settingPageViewModel.save { onSaved?.invoke() } }
            )
        }
    )
}
