package view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.entity.Device
import model.usecase.FetchDevicesUseCase
import model.usecase.IsRunningScrcpyUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyUseCase
import resource.Strings

@Composable
fun ConnectPage(
    fetchDevicesUseCase: FetchDevicesUseCase,
    startScrcpyUseCase: StartScrcpyUseCase,
    stopScrcpyUseCase: StopScrcpyUseCase,
    isRunningScrcpyUseCase: IsRunningScrcpyUseCase
) {
    val devices = fetchDevicesUseCase.execute()
    if (devices.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Text(
                Strings.NOT_FOUND_ANDROID_DEVICES,
                style = TextStyle(color = Color.Black, fontSize = 20.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        LazyColumn {
            items(
                devices,
                itemContent = { device ->
                    DeviceCard(
                        device,
                        startScrcpyUseCase,
                        stopScrcpyUseCase,
                        isRunningScrcpyUseCase
                    )
                }
            )
        }
    }

}

@Composable
fun DeviceCard(
    device: Device,
    startScrcpyUseCase: StartScrcpyUseCase,
    stopScrcpyUseCase: StopScrcpyUseCase,
    isRunningScrcpyUseCase: IsRunningScrcpyUseCase
) {
    var running by remember { mutableStateOf(isRunningScrcpyUseCase.execute(device)) }

    Card(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
        Box(modifier = Modifier.padding(8.dp)) {
            Button(
                onClick = {
                    if (running) {
                        running = false
                        stopScrcpyUseCase.execute(device)
                    } else {
                        running = true
                        startScrcpyUseCase.execute(device, null, onDestroy = { running = false })
                    }
                },
                modifier = Modifier.wrapContentSize().align(Alignment.BottomEnd)
            ) {
                Text(if (running) Strings.STOP else Strings.RUN)
            }

            Text(
                device.id,
                style = TextStyle(color = Color.Black, fontSize = 20.sp),
                modifier = Modifier.wrapContentSize().align(Alignment.CenterStart)
            )
        }
    }
}

