package view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import model.usecase.FetchDevicesUseCase
import model.usecase.IsRunningScrcpyUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyUseCase

@Composable
fun ConnectPage(
    fetchDevicesUseCase: FetchDevicesUseCase,
    startScrcpyUseCase: StartScrcpyUseCase,
    stopScrcpyUseCase: StopScrcpyUseCase,
    isRunningScrcpyUseCase: IsRunningScrcpyUseCase
) {
    var showMenu by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    val devices = fetchDevicesUseCase.execute()
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
            })
    }
}

