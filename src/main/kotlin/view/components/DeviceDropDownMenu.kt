package view.components.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import model.repository.ProcessStatus
import resource.Images
import resource.Strings

@Composable
fun DeviceDropDownMenu(
    processStatus: ProcessStatus,
    onSetting: () -> Unit = {},
    onScreenShot: () -> Unit = {},
    onStartRecording: () -> Unit = {},
    onStopRecording: () -> Unit = {},
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

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    onSetting()
                    expanded = false
                },
                modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = Strings.DEVICE_DROP_DOWN_PREFERENCE_MENU_TITLE, style = MaterialTheme.typography.body2
                )
            }

            DropdownMenuItem(
                onClick = {
                    onScreenShot()
                    expanded = false
                },
                modifier = Modifier.height(32.dp)
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
                },
                modifier = Modifier.height(32.dp)
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