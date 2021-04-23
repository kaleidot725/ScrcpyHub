package view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.entity.Device
import model.usecase.FetchDevicesUseCase
import model.usecase.SelectDeviceUseCase
import resource.Strings

@Composable
fun DeviceListView(
    fetchDevicesUseCase: FetchDevicesUseCase,
    selectDevicesUseCase: SelectDeviceUseCase
) {
    val devices = fetchDevicesUseCase.execute()
    val foundDevice = devices.isNotEmpty()
    var showMenu by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Text(text = Strings.DEVICE_TITLE, modifier = Modifier.padding(vertical = 8.dp))
    Text(
        devices.getOrNull(selectedIndex)?.getDeviceLabel() ?: Strings.NONE,
        modifier = Modifier.fillMaxWidth().clickable(onClick = { showMenu = true })
    )
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false },
        modifier = Modifier.fillMaxWidth().background(Color.White),
    ) {
        if (foundDevice) {
            devices.forEachIndexed { index, device ->
                DropdownMenuItem(
                    enabled = true,
                    onClick = {
                        selectedIndex = index
                        showMenu = false
                        selectDevicesUseCase.execute(device)
                    }
                ) {
                    Text(text = device.getDeviceLabel())
                }
            }
        } else {
            DropdownMenuItem(
                enabled = true,
                onClick = {
                    showMenu = false
                }
            ) {
                Text(text = Strings.NONE)
            }
        }
    }
}

private fun Device.getDeviceLabel(): String {
    return "$name ($id)"
}