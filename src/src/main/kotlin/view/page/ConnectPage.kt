package view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
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
    val devices = fetchDevicesUseCase.execute()

    devices.forEach { device ->
        Column {
            Text(device.name, style = TextStyle(color = Color.Black, fontSize = 24.sp))
//            DeviceListView(fetchDevicesUseCase, selectDeviceUseCase)
//            ResolutionListView(fetchResolutionsUseCase, selectResolutionUseCase)
//            RunAndStopButton(deviceRepository, resolutionRepository, startScrcpyUseCase, stopScrcpyUseCase)
        }
    }

}