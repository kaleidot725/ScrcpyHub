package view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import model.entity.Device
import model.repository.ProcessStatus
import resource.Images
import resource.Strings

@Composable
fun DeviceCard(
    context: Device.Context,
    processStatus: ProcessStatus,
    startScrcpy: ((Device.Context) -> Unit)? = null,
    stopScrcpy: ((Device.Context) -> Unit)? = null,
    goToDetail: ((Device.Context) -> Unit)? = null,
    takeScreenshot: ((Device.Context) -> Unit)? = null,
    startRecording: ((Device.Context) -> Unit)? = null,
    stopRecording: ((Device.Context) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.padding(horizontal = 8.dp).height(48.dp)) {
            Image(
                painter = painterResource(Images.DEVICE),
                contentDescription = Images.DEVICE,
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                modifier = Modifier.width(32.dp).align(Alignment.CenterVertically).padding(end = 4.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(fraction = 0.65f).align(Alignment.CenterVertically)
            ) {
                Text(
                    context.displayName,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                Text(
                    context.device.id,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            Button(
                onClick = {
                    when (processStatus) {
                        ProcessStatus.IDLE -> startScrcpy?.invoke(context)
                        ProcessStatus.RUNNING -> stopScrcpy?.invoke(context)
                        ProcessStatus.RECORDING -> {}
                    }
                },
                colors = when (processStatus) {
                    ProcessStatus.IDLE -> ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
                    ProcessStatus.RUNNING -> ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
                    ProcessStatus.RECORDING -> ButtonDefaults.buttonColors(MaterialTheme.colors.error)
                },
                modifier = Modifier.wrapContentHeight().width(80.dp).align(Alignment.CenterVertically)
            ) {
                Text(
                    text = when (processStatus) {
                        ProcessStatus.IDLE -> Strings.DEVICES_PAGE_START
                        ProcessStatus.RUNNING -> Strings.DEVICES_PAGE_STOP
                        ProcessStatus.RECORDING -> Strings.DEVICES_PAGE_RECORDING
                    },
                    style = MaterialTheme.typography.button
                )
            }

            DeviceDropDownMenu(
                processStatus = processStatus,
                onSetting = { goToDetail?.invoke(context) },
                onScreenShot = { takeScreenshot?.invoke(context) },
                onStartRecording = { startRecording?.invoke(context) },
                onStopRecording = { stopRecording?.invoke(context) },
                modifier = Modifier.width(30.dp).align(Alignment.CenterVertically).padding(start = 4.dp)
            )
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
        Image(
            painter = painterResource(Images.DOTS),
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
            modifier = Modifier.size(30.dp).clickable { expanded = true }
        )

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
                },
                onClick = {
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
                    },
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Preview
@Composable
private fun DeviceCard_Preview() {
    DeviceCard(Device.Context(Device("ID", "NAME")), ProcessStatus.IDLE)
}

@Preview
@Composable
private fun DeviceCard_Preview_Overflow() {
    val id = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
    val name = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
    DeviceCard(Device.Context(Device(id, name)), ProcessStatus.IDLE)
}
            