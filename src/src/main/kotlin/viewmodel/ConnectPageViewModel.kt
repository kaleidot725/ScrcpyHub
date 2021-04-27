package viewmodel

import model.usecase.FetchDevicesUseCase
import model.usecase.IsRunningScrcpyUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyUseCase
import org.koin.core.component.inject

class ConnectPageViewModel: ViewModel() {
    val fetchDevicesUseCase: FetchDevicesUseCase by inject()
    val startScrcpyUseCase: StartScrcpyUseCase by inject()
    val stopScrcpyUseCase: StopScrcpyUseCase by inject()
    val isRunningScrcpyUseCase: IsRunningScrcpyUseCase by inject()
}