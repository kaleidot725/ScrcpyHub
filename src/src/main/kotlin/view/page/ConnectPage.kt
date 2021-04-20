package view

import androidx.compose.runtime.Composable
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
    DeviceListView(fetchDevicesUseCase, selectDeviceUseCase)
    ResolutionListView(fetchResolutionsUseCase, selectResolutionUseCase)
    RunAndStopButton(deviceRepository, resolutionRepository, startScrcpyUseCase, stopScrcpyUseCase)
}