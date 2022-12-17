package view.pages.devices

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.entity.Device
import model.usecase.FetchDevicesUseCase
import model.usecase.GetDevicesFlowUseCase
import model.usecase.GetScrcpyStatusUseCase
import model.usecase.SaveScreenshotToDesktopUseCase
import model.usecase.StartAdbServerUseCase
import model.usecase.StartScrcpyRecordUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyRecordUseCase
import model.usecase.StopScrcpyUseCase
import view.StateHolder

class DevicesPageStateHolder(
    private val startAdbServerUseCase: StartAdbServerUseCase,
    private val fetchDevicesUseCase: FetchDevicesUseCase,
    private val getDevicesFlowUseCase: GetDevicesFlowUseCase,
    private val startScrcpyUseCase: StartScrcpyUseCase,
    private val stopScrcpyUseCase: StopScrcpyUseCase,
    private val startScrcpyRecordUseCase: StartScrcpyRecordUseCase,
    private val stopScrcpyRecordUseCase: StopScrcpyRecordUseCase,
    private val getScrcpyProcessStatusUseCase: GetScrcpyStatusUseCase,
    private val saveScreenshotToDesktop: SaveScreenshotToDesktopUseCase
) : StateHolder() {
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isStartingAdbServer: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val deviceStatusList: MutableStateFlow<List<DeviceStatus>> = MutableStateFlow(emptyList())
    val states: StateFlow<DevicesPageState> =
        combine(isLoading, isStartingAdbServer, deviceStatusList) { isLoading, isStartingAdbServer, deviceStatusList ->
            if (isLoading) return@combine DevicesPageState.Loading
            if (!isStartingAdbServer) return@combine DevicesPageState.Error
            return@combine if (deviceStatusList.isNotEmpty()) {
                DevicesPageState.DeviceExist(deviceStatusList)
            } else {
                DevicesPageState.DeviceIsEmpty
            }
        }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), DevicesPageState.Loading)

    override fun onStarted() {
        coroutineScope.launch {
            getDevicesFlowUseCase.get(coroutineScope).collect { updateStates(it) }
        }
        
        coroutineScope.launch {
            isLoading.value = true
            isStartingAdbServer.value = startAdbServerUseCase()
            isLoading.value = false

        }
    }

    fun startScrcpy(context: Device.Context) {
        coroutineScope.launch {
            startScrcpyUseCase.execute(context) { fetchStates() }
            fetchStates()
        }
    }

    fun stopScrcpy(context: Device.Context) {
        coroutineScope.launch {
            stopScrcpyUseCase.execute(context)
            fetchStates()
        }
    }

    fun startScrcpyRecord(context: Device.Context) {
        coroutineScope.launch {
            startScrcpyRecordUseCase.execute(context) { fetchStates() }
            fetchStates()
        }
    }

    fun stopScrcpyRecord(context: Device.Context) {
        coroutineScope.launch {
            stopScrcpyRecordUseCase.execute(context)
            fetchStates()
        }
    }

    fun saveScreenshotToDesktop(context: Device.Context) {
        coroutineScope.launch {
            saveScreenshotToDesktop.execute(context)
        }
    }

    private suspend fun fetchStates() {
        updateStates(fetchDevicesUseCase.execute())
    }

    private fun updateStates(contextList: List<Device.Context>) {
        deviceStatusList.value = contextList.map { context ->
            DeviceStatus(context, getScrcpyProcessStatusUseCase.execute(context))
        }
    }
}

