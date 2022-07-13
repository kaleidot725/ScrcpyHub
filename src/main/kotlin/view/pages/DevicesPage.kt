package view.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import view.components.organisms.DeviceList
import view.parts.Texts
import view.tab.PageHeader
import view.templates.HeaderAndContent
import viewmodel.DeviceStatus
import viewmodel.DevicesPageViewModel

@Composable
fun DevicesPage(
    windowScope: WindowScope,
    devicesPageViewModel: DevicesPageViewModel,
    onNavigateSetting: (() -> Unit)? = null,
    onNavigateDevice: ((Device.Context) -> Unit)? = null
) {
    val deviceStatusList: List<DeviceStatus> by devicesPageViewModel.states.collectAsState()

    HeaderAndContent(header = {
        PageHeader(windowScope = windowScope, title = Strings.APP_NAME, optionContent = {
            Image(
                painter = painterResource(Images.SETTING),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(18.dp).width(24.dp).clickable { onNavigateSetting?.invoke() }
            )
        })
    }, content = {
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
            )
        }
    })
}