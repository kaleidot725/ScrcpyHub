@file:JvmName("DeviceCardKt")

package view.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import model.entity.Device
import model.repository.ProcessStatus
import view.pages.devices.DeviceStatus
import view.resource.MainTheme

@Composable
fun DeviceCard(
    deviceStatus: DeviceStatus,
    startScrcpy: ((Device.Context) -> Unit),
    stopScrcpy: ((Device.Context) -> Unit),
    goToDetail: ((Device.Context) -> Unit),
    takeScreenshot: ((Device.Context) -> Unit),
    startRecording: ((Device.Context) -> Unit),
    stopRecording: ((Device.Context) -> Unit),
    modifier: Modifier = Modifier
) {
    Card(elevation = 4.dp, modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = deviceStatus.context.displayName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.weight(1.0f, true).align(Alignment.CenterVertically)
                )
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Screenshot",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { takeScreenshot.invoke(deviceStatus.context) }
                        .align(Alignment.CenterVertically)
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Setting",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { goToDetail.invoke(deviceStatus.context) }
                        .align(Alignment.CenterVertically)
                )
            }

            ScrcpyButtons(
                deviceStatus = deviceStatus,
                startScrcpy = startScrcpy,
                stopScrcpy = stopScrcpy,
                startRecording = startRecording,
                stopRecording = stopRecording
            )
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
                deviceStatus = DeviceStatus(context1, ProcessStatus.IDLE),
                startScrcpy = {},
                stopScrcpy = {},
                goToDetail = {},
                takeScreenshot = {},
                startRecording = {},
                stopRecording = {},
                modifier = Modifier
            )

            DeviceCard(
                deviceStatus = DeviceStatus(context2, ProcessStatus.RUNNING),
                startScrcpy = {},
                stopScrcpy = {},
                goToDetail = {},
                takeScreenshot = {},
                startRecording = {},
                stopRecording = {},
                modifier = Modifier
            )

            DeviceCard(
                deviceStatus = DeviceStatus(context3, ProcessStatus.RECORDING),
                startScrcpy = {},
                stopScrcpy = {},
                goToDetail = {},
                takeScreenshot = {},
                startRecording = {},
                stopRecording = {},
                modifier = Modifier
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
                deviceStatus = DeviceStatus(context1, ProcessStatus.IDLE),
                startScrcpy = {},
                stopScrcpy = {},
                goToDetail = {},
                takeScreenshot = {},
                startRecording = {},
                stopRecording = {},
                modifier = Modifier
            )

            DeviceCard(
                deviceStatus = DeviceStatus(context2, ProcessStatus.RUNNING),
                startScrcpy = {},
                stopScrcpy = {},
                goToDetail = {},
                takeScreenshot = {},
                startRecording = {},
                stopRecording = {},
                modifier = Modifier
            )

            DeviceCard(
                deviceStatus = DeviceStatus(context3, ProcessStatus.RECORDING),
                startScrcpy = {},
                stopScrcpy = {},
                goToDetail = {},
                takeScreenshot = {},
                startRecording = {},
                stopRecording = {},
                modifier = Modifier
            )
        }
    }
}
