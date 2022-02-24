package view.components.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import model.entity.Device
import resource.Images
import resource.Strings
import resource.Strings.DEVICES_PAGE_NOT_FOUND_DEVICES
import view.components.atoms.Texts
import view.components.organisms.DeviceList
import view.components.templates.DevicesTemplate
import view.tab.PageHeader
import viewmodel.DeviceStatus
import viewmodel.DevicesPageViewModel
import kotlin.system.exitProcess

@Composable
fun DevicesPage(
    windowScope: WindowScope,
    devicesPageViewModel: DevicesPageViewModel,
    onNavigateSetting: (() -> Unit)? = null,
    onNavigateDevice: ((Device.Context) -> Unit)? = null
) {
    val deviceStatusList: List<DeviceStatus> by devicesPageViewModel.states.collectAsState()

    DevicesTemplate(
        header = {
            PageHeader(windowScope = windowScope, title = Strings.APP_NAME, optionContent = {
                ApplicationDropDownMenu(
                    onSetting = { onNavigateSetting?.invoke() },
                    onQuit = { exitProcess(0) },
                    modifier = Modifier.height(18.dp).width(24.dp)
                )
            })
        },
        content = {
            if (deviceStatusList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Texts.Subtitle1(DEVICES_PAGE_NOT_FOUND_DEVICES, modifier = Modifier.align(Alignment.Center))
                }
            } else {
                DeviceList(
                    deviceStatusList = deviceStatusList,
                    startScrcpy = { devicesPageViewModel.startScrcpy(it) },
                    stopScrcpy = { devicesPageViewModel.stopScrcpy(it) },
                    goToDetail = { onNavigateDevice?.invoke(it) },
                    takeScreenshot = { devicesPageViewModel.saveScreenshotToDesktop(it) },
                    startRecording = { devicesPageViewModel.startScrcpyRecord(it) },
                    stopRecording = { devicesPageViewModel.stopScrcpyRecord(it) },
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                )
            }
        }
    )
}

@Composable
private fun ApplicationDropDownMenu(
    onSetting: () -> Unit, onQuit: () -> Unit, modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Image(painter = painterResource(Images.SETTING),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.matchParentSize().clickable { expanded = true })

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                onClick = {
                    onSetting()
                    expanded = false
                }, modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = Strings.DEVICES_DROP_DOWN_PREFERENCE_MENU_TITLE, style = MaterialTheme.typography.body2
                )
            }

            DropdownMenuItem(
                onClick = {
                    onQuit()
                    expanded = false
                }, modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = Strings.DEVICES_DROP_DOWN_QUIT_MENU_TITLE, style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
