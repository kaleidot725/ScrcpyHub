package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.entity.Device
import model.usecase.*

class DevicesPageViewModel(
    private val fetchDevicesUseCase: FetchDevicesUseCase,
    private val getDevicesFlowUseCase: GetDevicesFlowUseCase,
    private val startScrcpyUseCase: StartScrcpyUseCase,
    private val stopScrcpyUseCase: StopScrcpyUseCase,
    private val isRunningScrcpyUseCase: IsScrcpyRunningUseCase
) : ViewModel() {
    private val _states: MutableStateFlow<List<DeviceStatus>> = MutableStateFlow(emptyList())
    val states: StateFlow<List<DeviceStatus>> = _states

    override fun onStarted() {
        coroutineScope.launch {
            getDevicesFlowUseCase.get(coroutineScope).collect {
                updateStates(it)
            }
        }
    }

    fun startScrcpy(device: Device) {
        coroutineScope.launch {
            startScrcpyUseCase.execute(device) { fetchStates() }
            fetchStates()
        }
    }

    fun stopScrcpy(device: Device) {
        coroutineScope.launch {
            stopScrcpyUseCase.execute(device)
            fetchStates()
        }
    }

    private suspend fun fetchStates() {
        updateStates(fetchDevicesUseCase.execute())
    }

    private fun updateStates(devices: List<Device>) {
        _states.value = devices.map { device -> DeviceStatus(device, isRunningScrcpyUseCase.execute(device)) }
    }
}

data class DeviceStatus(val device: Device, val isRunning: Boolean)
