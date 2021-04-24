package view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

