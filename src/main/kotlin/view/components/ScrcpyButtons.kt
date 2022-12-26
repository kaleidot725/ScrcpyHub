package view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.entity.Device
import model.repository.ProcessStatus
import view.pages.devices.DeviceStatus
import view.parts.MenuButton
import view.parts.MenuButtonColors
import view.parts.MenuButtonStatus
import view.resource.Strings

@Composable
fun ScrcpyButtons(
    deviceStatus: DeviceStatus,
    startScrcpy: ((Device.Context) -> Unit),
    stopScrcpy: ((Device.Context) -> Unit),
    startRecording: ((Device.Context) -> Unit),
    stopRecording: ((Device.Context) -> Unit),
) {
    Card {
        Row(modifier = Modifier.height(30.dp).fillMaxWidth()) {
            MenuButton(
                text = if (deviceStatus.processStatus is ProcessStatus.Recording)
                    Strings.DEVICES_PAGE_STOP_RECORDING
                else
                    Strings.DEVICES_PAGE_START_RECORDING,
                style = MaterialTheme.typography.subtitle2,
                status = RecordingButtonStatus(deviceStatus.processStatus),
                colors = RecordingButtonColors(),
                onIdleClick = { startRecording.invoke(deviceStatus.context) },
                onActiveClick = { stopRecording.invoke(deviceStatus.context) },
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxHeight()
            )

            Box(
                modifier = Modifier.fillMaxHeight().width(1.dp)
                    .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
            )

            MenuButton(
                text = if (deviceStatus.processStatus is ProcessStatus.Running)
                    Strings.DEVICES_PAGE_STOP_MIRRORING
                else
                    Strings.DEVICES_PAGE_START_MIRRORING,
                style = MaterialTheme.typography.subtitle2,
                status = StartButtonStatus(deviceStatus.processStatus),
                colors = StartButtonColors(),
                onIdleClick = { startScrcpy.invoke(deviceStatus.context) },
                onActiveClick = { stopScrcpy.invoke(deviceStatus.context) },
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
private fun CaptureButtonStatus(): MenuButtonStatus {
    return MenuButtonStatus.ENABLE
}

@Composable
private fun CaptureButtonColors(): MenuButtonColors {
    return MenuButtonColors(
        active = MaterialTheme.colors.primary,
        enable = MaterialTheme.colors.primary,
        disable = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
        textColor = MaterialTheme.colors.onPrimary,
        textColorOnDisable = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
    )
}

@Composable
private fun RecordingButtonStatus(processStatus: ProcessStatus): MenuButtonStatus {
    return when (processStatus) {
        is ProcessStatus.Recording -> MenuButtonStatus.ACTIVE
        ProcessStatus.Idle -> MenuButtonStatus.ENABLE
        is ProcessStatus.Running -> MenuButtonStatus.DISABLE
    }
}

@Composable
private fun RecordingButtonColors(): MenuButtonColors {
    return MenuButtonColors(
        active = MaterialTheme.colors.error,
        enable = MaterialTheme.colors.primary,
        disable = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
        textColor = MaterialTheme.colors.onPrimary,
        textColorOnDisable = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
    )
}

@Composable
private fun StartButtonStatus(processStatus: ProcessStatus): MenuButtonStatus {
    return when (processStatus) {
        is ProcessStatus.Recording -> MenuButtonStatus.DISABLE
        ProcessStatus.Idle -> MenuButtonStatus.ENABLE
        is ProcessStatus.Running -> MenuButtonStatus.ACTIVE
    }
}

@Composable
private fun StartButtonColors(): MenuButtonColors {
    return MenuButtonColors(
        active = MaterialTheme.colors.error,
        enable = MaterialTheme.colors.primary,
        disable = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
        textColor = MaterialTheme.colors.onPrimary,
        textColorOnDisable = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
    )
}

@Composable
private fun StopButtonStatus(processStatus: ProcessStatus): MenuButtonStatus {
    return when (processStatus) {
        is ProcessStatus.Recording -> MenuButtonStatus.ENABLE
        is ProcessStatus.Idle -> MenuButtonStatus.DISABLE
        is ProcessStatus.Running -> MenuButtonStatus.ENABLE
    }
}

@Composable
private fun StopButtonColors(): MenuButtonColors {
    return MenuButtonColors(
        active = MaterialTheme.colors.primary,
        enable = MaterialTheme.colors.primary,
        disable = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
        textColor = MaterialTheme.colors.onPrimary,
        textColorOnDisable = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
    )
}
