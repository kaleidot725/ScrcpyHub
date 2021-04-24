package view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.entity.Device
import model.entity.Resolution
import resource.Strings

@Composable
fun DeviceCard(device: Device, resolutions: List<Resolution>) {
    var showMenu by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Card(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(8.dp)) {
        Box(modifier = Modifier.padding(8.dp)) {
            Button(
                onClick = {},
                modifier = Modifier.wrapContentSize().align(Alignment.BottomEnd)
            ) {
                Text(Strings.RUN)
            }

            Column(modifier = Modifier.wrapContentSize()) {
                Text(
                    device.getDeviceLabel(),
                    style = TextStyle(color = Color.Black, fontSize = 24.sp),
                )

                Text(
                    resolutions[selectedIndex].getResolutionLabel(),
                    style = TextStyle(color = Color.Black, fontSize = 24.sp),
                    modifier = Modifier.clickable(onClick = { showMenu = true })
                )

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.wrapContentSize().background(Color.White),
                ) {
                    resolutions.forEachIndexed { index, resolution ->
                        DropdownMenuItem(
                            enabled = true,
                            onClick = {
                                selectedIndex = index
                                showMenu = false
                            }
                        ) {
                            Text(text = resolution.getResolutionLabel())
                        }
                    }
                }
            }
        }

    }
}

private fun Device.getDeviceLabel(): String {
    return "$name ($id)"
}

private fun Resolution.getResolutionLabel(): String {
    return "$name ($width x $height)"
}