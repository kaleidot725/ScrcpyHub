package view.page

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import resource.Strings.SETTING_PAGE_EDIT_ADB_LOCATION_DETAILS
import resource.Strings.SETTING_PAGE_EDIT_ADB_LOCATION_TITLE
import resource.Strings.SETTING_PAGE_EDIT_SCRCPY_LOCATION_DETAILS
import resource.Strings.SETTING_PAGE_EDIT_SCRCPY_LOCATION_TITLE
import resource.Strings.SETTING_PAGE_EDIT_THEME_DETAILS
import resource.Strings.SETTING_PAGE_EDIT_THEME_TITLE
import view.components.SaveButton
import view.extention.onInitialize
import view.tab.PageHeader
import viewmodel.SettingPageViewModel

@Composable
fun SettingPage(
    windowScope: WindowScope,
    settingPageViewModel: SettingPageViewModel,
    onNavigateDevices: (() -> Unit)? = null,
    onSaved: (() -> Unit)? = null
) {
    onInitialize(settingPageViewModel)
    onDrawPage(windowScope, settingPageViewModel, onNavigateDevices, onSaved)
}

@Composable
private fun onDrawPage(
    windowScope: WindowScope,
    viewModel: SettingPageViewModel,
    onNavigateDevice: (() -> Unit)? = null,
    onSaved: (() -> Unit)? = null
) {
    val adbLocation: String by viewModel.adbLocation.collectAsState()
    val scrcpyLocation: String by viewModel.scrcpyLocation.collectAsState()
    val theme: Theme? by viewModel.theme.collectAsState()
    val themes: List<Theme> by viewModel.themes.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
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
                        .clickable { onNavigateDevice?.invoke() }
                )
            }
        )

        ThemeSetting(
            currentTheme = theme,
            themes = themes,
            onUpdate = { viewModel.updateTheme(it) },
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        AdbLocationSetting(
            adbLocation = adbLocation,
            onUpdate = { viewModel.updateAdbLocation(it) },
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        ScrcpyLocationSetting(
            scrcpyLocation = scrcpyLocation,
            onUpdate = { viewModel.updateScrcpyLocation(it) },
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        SaveButton(
            savable = true,
            modifier = Modifier.padding(horizontal = 8.dp),
            onSaved = { viewModel.save() { onSaved?.invoke() } }
        )
    }
}

@Composable
private fun ThemeSetting(
    currentTheme: Theme?,
    themes: List<Theme>,
    onUpdate: (Theme) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                SETTING_PAGE_EDIT_THEME_TITLE,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                SETTING_PAGE_EDIT_THEME_DETAILS,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 8.dp)) {
                Row {
                    themes.forEach { theme ->
                        Row(modifier = Modifier.wrapContentSize()) {
                            RadioButton(
                                selected = (theme == currentTheme),
                                onClick = { onUpdate(theme) },
                                modifier = Modifier.size(16.dp)
                            )

                            Text(
                                text = theme.toLabel(),
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun Theme.toLabel(): String {
    return when (this) {
        Theme.LIGHT -> Strings.SETTING_THEME_LIGHT
        Theme.DARK -> Strings.SETTING_THEME_DARK
        Theme.SYNC_WITH_OS -> Strings.SETTING_THEME_SYNC_WITH_OS
    }
}

@Preview
@Composable
private fun ThemeSetting_Preview() {
    ThemeSetting(Theme.SYNC_WITH_OS, Theme.values().toList(), {}, Modifier)
}

@Composable
private fun AdbLocationSetting(adbLocation: String, onUpdate: (String) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                SETTING_PAGE_EDIT_ADB_LOCATION_TITLE,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                SETTING_PAGE_EDIT_ADB_LOCATION_DETAILS,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = adbLocation,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onUpdate(it) },
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun AdbLocationSetting_Preview() {
    AdbLocationSetting("TEST", {})
}

@Composable
private fun ScrcpyLocationSetting(
    scrcpyLocation: String,
    onUpdate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                SETTING_PAGE_EDIT_SCRCPY_LOCATION_TITLE,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                SETTING_PAGE_EDIT_SCRCPY_LOCATION_DETAILS,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = scrcpyLocation,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onUpdate(it) },
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun ScrcpyLocationSetting_Preview() {
    ScrcpyLocationSetting("TEST", {})
}
