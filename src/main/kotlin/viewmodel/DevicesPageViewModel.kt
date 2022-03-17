package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.entity.Device
import model.repository.ProcessStatus
import model.usecase.FetchDevicesUseCase
import model.usecase.GetDevicesFlowUseCase
import model.usecase.GetScrcpyStatusUseCase
import model.usecase.SaveScreenshotToDesktopUseCase
import model.usecase.StartScrcpyRecordUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyRecordUseCase
import model.usecase.StopScrcpyUseCase

class DevicesPageViewModel(
    private val fetchDevicesUseCase: FetchDevicesUseCase,
    private val getDevicesFlowUseCase: GetDevicesFlowUseCase,
    private val startScrcpyUseCase: StartScrcpyUseCase,
    private val stopScrcpyUseCase: StopScrcpyUseCase,
    private val startScrcpyRecordUseCase: StartScrcpyRecordUseCase,
    private val stopScrcpyRecordUseCase: StopScrcpyRecordUseCase,
    private val getScrcpyProcessStatusUseCase: GetScrcpyStatusUseCase,
    private val saveScreenshotToDesktop: SaveScreenshotToDesktopUseCase
) : ViewModel() {
    private val _states: MutableStateFlow<List<DeviceStatus>> = MutableStateFlow(emptyList())
    val states: StateFlow<List<DeviceStatus>> = _states

    override fun onStarted() {
        coroutineScope.launch {
            fetchStates()
            getDevicesFlowUseCase.get(coroutineScope).collect {
                updateStates(it)
            }
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
        _states.value = contextList.map { context ->
            DeviceStatus(context, getScrcpyProcessStatusUseCase.execute(context))
        }
    }
}

data class DeviceStatus(val context: Device.Context, val processStatus: ProcessStatus)
