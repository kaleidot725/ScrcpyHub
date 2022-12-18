@file:JvmName("DeviceCardKt")

package view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Screenshot
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.entity.Device
import model.repository.ProcessStatus
import view.pages.devices.DeviceStatus
import view.parts.Texts
import view.parts.TitleAndIcon
import view.resource.MainTheme

@Composable
fun DeviceCard(
    deviceStatus: DeviceStatus,
    startScrcpy: ((Device.Context) -> Unit)? = null,
    stopScrcpy: ((Device.Context) -> Unit)? = null,
    goToDetail: ((Device.Context) -> Unit)? = null,
    takeScreenshot: ((Device.Context) -> Unit)? = null,
    startRecording: ((Device.Context) -> Unit)? = null,
    stopRecording: ((Device.Context) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(elevation = 4.dp, modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        ) {
            Row(modifier = Modifier.padding(horizontal = 4.dp)) {
                Texts.Subtitle1(
                    text = deviceStatus.context.displayName,
                    maxLines = 1,
                    modifier = Modifier.weight(1.0f, true).align(Alignment.CenterVertically)
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Setting",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { goToDetail?.invoke(deviceStatus.context) }
                        .align(Alignment.CenterVertically)
                )
            }

            Row(
                modifier = Modifier
                    .height(30.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                TitleAndIcon(
                    title = "Capture",
                    iconVector = Icons.Default.Screenshot,
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxHeight()
                        .clickable {
                            takeScreenshot?.invoke(deviceStatus.context)
                        }
                )

                Box(
                    modifier = Modifier.fillMaxHeight().width(1.dp)
                        .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
                )

                TitleAndIcon(
                    title = "Record",
                    iconVector = Icons.Default.Radio,
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxHeight()
                        .clickable(enabled = (deviceStatus.processStatus == ProcessStatus.IDLE)) {
                            startRecording?.invoke(deviceStatus.context)
                        }
                )

                Box(
                    modifier = Modifier.fillMaxHeight().width(1.dp)
                        .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
                )

                TitleAndIcon(
                    title = "Start",
                    iconVector = Icons.Default.PlayArrow,
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxHeight()
                        .clickable(enabled = (deviceStatus.processStatus == ProcessStatus.IDLE)) {
                            startScrcpy?.invoke(deviceStatus.context)
                        }
                )

                Box(
                    modifier = Modifier.fillMaxHeight().width(1.dp)
                        .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
                )

                TitleAndIcon(
                    title = "Stop",
                    iconVector = Icons.Default.Stop,
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxHeight()
                        .clickable {
                            if (deviceStatus.processStatus == ProcessStatus.RECORDING) {
                                stopRecording?.invoke(deviceStatus.context)
                                return@clickable
                            }
                            if (deviceStatus.processStatus == ProcessStatus.RUNNING) {
                                stopScrcpy?.invoke(deviceStatus.context)
                                return@clickable
                            }
                        }
                )
            }
        }
    }
}

@Preview
@Composable
private fun DeviceCard_Preview_DARK() {
    val device = Device("00001")
    val context1 = Device.Context(device)
    val context2 = Device.Context(device, "CUSTOM_NAME1")
    val context3 = Device.Context(device, "CUSTOM_NAME2")

    MainTheme(isDarkMode = true) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            DeviceCard(
                DeviceStatus(context1, ProcessStatus.IDLE)
            )

            DeviceCard(
                DeviceStatus(context2, ProcessStatus.RUNNING)
            )

            DeviceCard(
                DeviceStatus(context3, ProcessStatus.RECORDING)
            )
        }
    }
}

@Preview
@Composable
private fun DeviceCard_Preview_Light() {
    val device = Device("00001")
    val context1 = Device.Context(device)
    val context2 = Device.Context(device, "CUSTOM_NAME1")
    val context3 = Device.Context(device, "CUSTOM_NAME2")

    MainTheme(isDarkMode = false) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            DeviceCard(
                DeviceStatus(context1, ProcessStatus.IDLE)
            )

            DeviceCard(
                DeviceStatus(context2, ProcessStatus.RUNNING)
            )

            DeviceCard(
                DeviceStatus(context3, ProcessStatus.RECORDING)
            )
        }
    }
}
