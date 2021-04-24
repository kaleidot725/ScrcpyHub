package view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import model.repository.DeviceRepository
import model.repository.ResolutionRepository
import model.usecase.*

@Composable
fun ConnectPage(
    fetchDevicesUseCase: FetchDevicesUseCase,
    selectDeviceUseCase: SelectDeviceUseCase,
    fetchResolutionsUseCase: FetchResolutionsUseCase,
    selectResolutionUseCase: SelectResolutionUseCase,
    deviceRepository: DeviceRepository,
    resolutionRepository: ResolutionRepository,
    startScrcpyUseCase: StartScrcpyUseCase,
    stopScrcpyUseCase: StopScrcpyUseCase
) {
    var showMenu by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Column {
        val devices = fetchDevicesUseCase.execute()
        devices.forEach { device ->
            DeviceCard(device, startScrcpyUseCase, stopScrcpyUseCase)
        }
    }
}

