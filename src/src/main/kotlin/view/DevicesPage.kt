package view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import model.entity.Device
import model.usecase.FetchDevicesUseCase
import model.usecase.IsRunningScrcpyUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyUseCase
import resource.Images
import resource.Strings

@Composable
fun ConnectPage(
    fetchDevicesUseCase: FetchDevicesUseCase,
    startScrcpyUseCase: StartScrcpyUseCase,
    stopScrcpyUseCase: StopScrcpyUseCase,
    isRunningScrcpyUseCase: IsRunningScrcpyUseCase
) {
    var devices by remember { mutableStateOf(fetchDevicesUseCase.execute()) }

    Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        if (devices.isEmpty()) {
            Text(
                Strings.NOT_FOUND_ANDROID_DEVICES,
                style = TextStyle(color = Color.Black, fontSize = 20.sp),
                modifier = Modifier.align(Alignment.Center)
            )
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

        FloatingActionButton(
            onClick = { devices = fetchDevicesUseCase.execute() },
            modifier = Modifier.align(Alignment.BottomEnd).width(50.dp).height(50.dp)
        ) {
            Image(
                imageFromResource(Images.RESTART_BLACK),
                Images.RESTART_BLACK,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.padding(8.dp)
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
    val coroutineScope: CoroutineScope = MainScope() // FIXME
    var running by remember { mutableStateOf(isRunningScrcpyUseCase.execute(device)) }

    DisposableEffect(Unit) {
        onDispose {
            coroutineScope.cancel("") // FIXME
        }
    }

    Card(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
        Box(modifier = Modifier.padding(8.dp)) {
            Button(
                onClick = {
                    if (running) {
                        running = false
                        coroutineScope.launch {
                            stopScrcpyUseCase.execute(device)
                        }
                    } else {
                        running = true
                        coroutineScope.launch {
                            startScrcpyUseCase.execute(device, null, onDestroy = { running = false })
                        }
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

