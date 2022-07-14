package view.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import model.entity.Device
import view.components.organisms.DeviceList
import view.parts.Texts
import view.resource.Images
import view.resource.Strings
import view.resource.Strings.DEVICES_PAGE_NOT_FOUND_DEVICES
import view.tab.PageHeader
import view.templates.HeaderAndContent

@Composable
fun DevicesPage(
    windowScope: WindowScope,
    stateHolder: DevicesPageStateHolder,
    onNavigateSetting: (() -> Unit)? = null,
    onNavigateDevice: ((Device.Context) -> Unit)? = null
) {
    val deviceStatusList: List<DeviceStatus> by stateHolder.states.collectAsState()

    DisposableEffect(stateHolder) {
        stateHolder.onStarted()
        onDispose {
            stateHolder.onCleared()
        }
    }

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
                startScrcpy = { stateHolder.startScrcpy(it) },
                stopScrcpy = { stateHolder.stopScrcpy(it) },
                goToDetail = { onNavigateDevice?.invoke(it) },
                takeScreenshot = { stateHolder.saveScreenshotToDesktop(it) },
                startRecording = { stateHolder.startScrcpyRecord(it) },
                stopRecording = { stateHolder.stopScrcpyRecord(it) },
            )
        }
    })
}
