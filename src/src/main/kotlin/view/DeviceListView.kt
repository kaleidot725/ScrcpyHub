package view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import model.Device
import model.repository.DeviceRepository

@Composable
fun DeviceListView(deviceRepository: DeviceRepository) {
    val devices = deviceRepository.getAll()
    var showMenu by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    DropdownMenu(
        toggle = {
            Text(
                devices[selectedIndex].getDeviceLabel(),
                modifier = Modifier.fillMaxWidth().clickable(onClick = { showMenu = true })
            )
        },
        expanded = showMenu,
        onDismissRequest = { showMenu = false },
        toggleModifier = Modifier.fillMaxWidth().background(Color.Transparent),
        dropdownModifier = Modifier.fillMaxWidth().background(Color.Red)
    ) {
        devices.forEachIndexed { index, device ->
            DropdownMenuItem(
                enabled = true,
                onClick = {
                    selectedIndex = index
                    showMenu = false
                }
            ) {
                Text(text = device.getDeviceLabel())
            }
        }
    }
}

private fun Device.getDeviceLabel(): String {
    return "$name ($id)"
}