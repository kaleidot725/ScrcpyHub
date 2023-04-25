package view.pages.devices

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.WindowScope
import model.entity.Device
import view.components.DevicePager
import view.parts.Texts
import view.parts.TopPageMiniHeader
import view.resource.Strings
import view.resource.Strings.DEVICES_PAGE_ERROR_STARTING_ADB_SERVER
import view.resource.Strings.DEVICES_PAGE_NOT_FOUND_DEVICES
import view.templates.MainLayout

@Composable
fun DevicesPageForMini(
    windowScope: WindowScope,
    stateHolder: DevicesPageStateHolder,
    onNavigateSetting: (() -> Unit)? = null,
    onNavigateDevice: ((Device.Context) -> Unit)? = null
) {
    val state: DevicesPageState by stateHolder.states.collectAsState()

    DisposableEffect(stateHolder) {
        stateHolder.onStarted()
        onDispose {
            stateHolder.onCleared()
        }
    }

    MainLayout(
        header = {
            TopPageMiniHeader(
                windowScope = windowScope,
                title = Strings.APP_NAME
            )
        },
        content = {
            when (val state = state) {
                DevicesPageState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                DevicesPageState.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Texts.Subtitle1(
                            DEVICES_PAGE_ERROR_STARTING_ADB_SERVER,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                is DevicesPageState.DeviceExist -> {
                    DevicePager(
                        deviceStatusList = state.devices,
                        startScrcpy = { stateHolder.startScrcpy(it) },
                        stopScrcpy = { stateHolder.stopScrcpy(it) },
                        goToDetail = { onNavigateDevice?.invoke(it) },
                        takeScreenshot = { stateHolder.saveScreenshotToDesktop(it) },
                        startRecording = { stateHolder.startScrcpyRecord(it) },
                        stopRecording = { stateHolder.stopScrcpyRecord(it) },
                    )
                }

                DevicesPageState.DeviceIsEmpty -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Texts.Subtitle1(DEVICES_PAGE_NOT_FOUND_DEVICES, modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    )
}
