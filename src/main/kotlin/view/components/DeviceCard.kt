package view.components.organisms

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.entity.Device
import model.repository.ProcessStatus
import view.MainTheme
import view.components.atoms.SmallIcon
import view.components.molecules.TitleAndActionButton
import view.resource.Images
import view.resource.Strings
import viewmodel.DeviceStatus

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
        Row(modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
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
                modifier = Modifier.weight(0.9f)
            )

            DeviceDropDownMenu(
                processStatus = deviceStatus.processStatus,
                onSetting = { goToDetail?.invoke(deviceStatus.context) },
                onScreenShot = { takeScreenshot?.invoke(deviceStatus.context) },
                onStartRecording = { startRecording?.invoke(deviceStatus.context) },
                onStopRecording = { stopRecording?.invoke(deviceStatus.context) },
                modifier = Modifier
                    .width(30.dp)
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun DeviceCard_Preview_DARK() {
    val device = Device("00001", "DEFAULT_NAME")
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
    val device = Device("00001", "DEFAULT_NAME")
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
