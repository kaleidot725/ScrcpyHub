package view.page

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import resource.Images
import resource.Strings
import resource.Strings.ADB_LOCATION
import resource.Strings.IF_ADB_LOCATION_IS_EMPTY
import resource.Strings.IF_SCRCPY_LOCATION_IS_EMPTY
import resource.Strings.SCRCPY_LOCATION
import view.components.SaveButton
import view.extention.onInitialize
import view.tab.PageHeader
import viewmodel.SettingPageViewModel

@Composable
fun SettingPage(
    settingPageViewModel: SettingPageViewModel,
    onNavigateDevices: (() -> Unit)? = null,
    onSaved: (() -> Unit)? = null
) {
    onInitialize(settingPageViewModel)
    onDrawPage(settingPageViewModel, onNavigateDevices, onSaved)
}

@Composable
private fun onDrawPage(
    viewModel: SettingPageViewModel,
    onNavigateDevice: (() -> Unit)? = null,
    onSaved: (() -> Unit)? = null
) {
    val adbLocation: String by viewModel.adbLocation.collectAsState()
    val scrcpyLocation: String by viewModel.scrcpyLocation.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        PageHeader(
            title = Strings.SETTING_PAGE_TITLE,
            icon = painterResource(Images.CLOSE),
            onAction = { onNavigateDevice?.invoke() }
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

        SaveButton(modifier = Modifier.padding(horizontal = 8.dp), onSaved = { viewModel.save() { onSaved?.invoke() } })
    }
}

@Composable
private fun AdbLocationSetting(adbLocation: String, onUpdate: (String) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(ADB_LOCATION, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(IF_ADB_LOCATION_IS_EMPTY, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = adbLocation,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onUpdate(it) }
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
            Text(SCRCPY_LOCATION, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(
                IF_SCRCPY_LOCATION_IS_EMPTY,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = scrcpyLocation,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { onUpdate(it) }
            )
        }
    }
}

@Preview
@Composable
private fun ScrcpyLocationSetting_Preview() {
    ScrcpyLocationSetting("TEST", {})
}