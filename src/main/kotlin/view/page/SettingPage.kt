package view.page

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
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
import resource.Images
import resource.Strings
import resource.Strings.SETTING_PAGE_EDIT_ADB_LOCATION_DETAILS
import resource.Strings.SETTING_PAGE_EDIT_ADB_LOCATION_TITLE
import resource.Strings.SETTING_PAGE_EDIT_SCRCPY_LOCATION_DETAILS
import resource.Strings.SETTING_PAGE_EDIT_SCRCPY_LOCATION_TITLE
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
private fun ScrcpyLocationSetting(scrcpyLocation: String, onUpdate: (String) -> Unit, modifier: Modifier = Modifier) {
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
