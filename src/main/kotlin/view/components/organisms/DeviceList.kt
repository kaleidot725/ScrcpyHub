package view.components.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import model.entity.Device
import model.repository.ProcessStatus
import resource.Images
import resource.Strings
import view.components.atoms.SmallIcon
import view.components.molecules.TitleAndActionButton
import viewmodel.DeviceStatus

@Composable
fun DeviceList(
    deviceStatusList: List<DeviceStatus>,
    modifier: Modifier = Modifier,
    startScrcpy: ((Device.Context) -> Unit)? = null,
    stopScrcpy: ((Device.Context) -> Unit)? = null,
    goToDetail: ((Device.Context) -> Unit)? = null,
    takeScreenshot: ((Device.Context) -> Unit)? = null,
    startRecording: ((Device.Context) -> Unit)? = null,
    stopRecording: ((Device.Context) -> Unit)? = null,
) {
    LazyColumn(modifier = modifier) {
        items(deviceStatusList) { deviceStatus ->
            Card {
                Row(modifier = Modifier.padding(horizontal = 8.dp).height(48.dp).fillMaxWidth()) {
                    SmallIcon(
                        filePath = Images.DEVICE,
                        description = Images.DEVICE,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    TitleAndActionButton(
                        subtitle1 = deviceStatus.context.displayName,
                        subtitle2 = deviceStatus.context.device.id,
                        actionButtonText = when (deviceStatus.processStatus) {
                            ProcessStatus.IDLE -> Strings.DEVICES_PAGE_START
                            ProcessStatus.RUNNING -> Strings.DEVICES_PAGE_STOP
                            ProcessStatus.RECORDING -> Strings.DEVICES_PAGE_RECORDING
                        },
                        actionButtonColors = when (deviceStatus.processStatus) {
                            ProcessStatus.IDLE -> ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
                            ProcessStatus.RUNNING -> ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
                            ProcessStatus.RECORDING -> ButtonDefaults.buttonColors(MaterialTheme.colors.error)
                        },
                        onClickActionButton = {
                            when (deviceStatus.processStatus) {
                                ProcessStatus.IDLE -> startScrcpy?.invoke(deviceStatus.context)
                                ProcessStatus.RUNNING -> stopScrcpy?.invoke(deviceStatus.context)
                                ProcessStatus.RECORDING -> {}
                            }
                        },
                        modifier = Modifier.padding(8.dp).fillMaxWidth(fraction = 0.90f)
                    )

                    DeviceDropDownMenu(
                        processStatus = ProcessStatus.IDLE,
                        onSetting = { goToDetail?.invoke(deviceStatus.context) },
                        onScreenShot = { takeScreenshot?.invoke(deviceStatus.context) },
                        onStartRecording = { startRecording?.invoke(deviceStatus.context) },
                        onStopRecording = { stopRecording?.invoke(deviceStatus.context) },
                        modifier = Modifier.width(30.dp).align(Alignment.CenterVertically).padding(start = 4.dp)
                    )
                }
            }
        }
    }
}


@Composable
private fun DeviceDropDownMenu(
    processStatus: ProcessStatus,
    onSetting: () -> Unit,
    onScreenShot: () -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Image(painter = painterResource(Images.DOTS),
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
            modifier = Modifier.size(30.dp).clickable { expanded = true })

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                onClick = {
                    onSetting()
                    expanded = false
                }, modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = Strings.DEVICE_DROP_DOWN_PREFERENCE_MENU_TITLE, style = MaterialTheme.typography.body2
                )
            }

            DropdownMenuItem(
                onClick = {
                    onScreenShot()
                    expanded = false
                }, modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = Strings.DEVICE_DROP_DOWN_SCREEN_SHOT_MENU_TITLE, style = MaterialTheme.typography.body2
                )
            }

            DropdownMenuItem(
                enabled = when (processStatus) {
                    ProcessStatus.IDLE -> true
                    ProcessStatus.RUNNING -> false
                    ProcessStatus.RECORDING -> true
                }, onClick = {
                    when (processStatus) {
                        ProcessStatus.IDLE -> onStartRecording.invoke()
                        ProcessStatus.RUNNING -> Unit
                        ProcessStatus.RECORDING -> onStopRecording.invoke()
                    }
                    expanded = false
                }, modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = when (processStatus) {
                        ProcessStatus.IDLE -> Strings.DEVICE_DROP_DOWN_START_RECORDING_MENU_TITLE
                        ProcessStatus.RUNNING -> Strings.DEVICE_DROP_DOWN_START_RECORDING_MENU_TITLE
                        ProcessStatus.RECORDING -> Strings.DEVICE_DROP_DOWN_STOP_RECORDING_MENU_TITLE
                    }, style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
