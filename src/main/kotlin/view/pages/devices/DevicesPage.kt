package view.pages.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import model.entity.Device
import model.entity.Message
import view.components.DeviceList
import view.parts.Texts
import view.parts.TopPageHeader
import view.resource.Strings
import view.resource.Strings.DEVICES_PAGE_ERROR_STARTING_ADB_SERVER
import view.resource.Strings.DEVICES_PAGE_NOT_FOUND_DEVICES
import view.templates.MainLayout

@Composable
fun DevicesPage(
    windowScope: WindowScope,
    stateHolder: DevicesPageStateHolder,
    onNavigateSetting: (() -> Unit)? = null,
    onNavigateDevice: ((Device.Context) -> Unit)? = null
) {
    val state: DevicesPageState by stateHolder.states.collectAsState()
    val messages: List<Message> by stateHolder.messages.collectAsState()

    DisposableEffect(stateHolder) {
        stateHolder.onStarted()
        onDispose {
            stateHolder.onCleared()
        }
    }

    MainLayout(
        header = {
            TopPageHeader(
                windowScope = windowScope,
                title = Strings.APP_NAME,
                onClickOption = { onNavigateSetting?.invoke() }
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
                    DeviceList(
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
        },
        snackBar = {
            EventMessageList(messages)
        }
    )
}

@Composable
private fun EventMessageList(messages: List<Message>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(messages, key = { it.uuid }) {
            val backgroundColor = when (it) {
                is Message.Error -> MaterialTheme.colors.error
                else -> MaterialTheme.colors.primary
            }
            val textColor = when (it) {
                is Message.Error -> MaterialTheme.colors.onError
                else -> MaterialTheme.colors.onPrimary
            }
            Card(backgroundColor = backgroundColor) {
                Text(
                    text = it.toUIMessage(),
                    color = textColor,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp)
                )
            }
        }
    }
}

private fun Message.toUIMessage(): String {
    return when (this) {
        Message.Error.NotFoundAdbBinary -> "Not found adb binary,\nPlease setup adb binary location"
        Message.Error.NotFoundScrcpyBinary -> "Not found scrcpy binary,\nPlease setup scrcpy binary location"
        is Message.Notify.FailedToSaveScreenshot -> "Failed to save ${this.context.displayName} Screenshot!"
        is Message.Notify.StartRecordingMovie -> "Start recording movie on ${this.context.displayName}"
        is Message.Notify.StopRecordingMovie -> "Stop recording movie on ${this.context.displayName}"
        is Message.Notify.FailedRecordingMovie -> "Failed recording movie on ${this.context.displayName}"
        is Message.Notify.SuccessToSaveScreenshot -> "Success to save ${this.context.displayName} Screenshot!"
        is Message.Notify.StartMirroring -> "Start mirroring on ${this.context.displayName}"
        is Message.Notify.StopMirroring -> "Stop mirroring on ${this.context.displayName}"
        is Message.Notify.FailedMirroring -> "Failed mirroring on ${this.context.displayName}"
    }
}
