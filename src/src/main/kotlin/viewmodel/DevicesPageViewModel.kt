package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.entity.Device
import model.usecase.FetchDevicesUseCase
import model.usecase.IsRunningScrcpyUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyUseCase
import org.koin.core.component.inject

class DevicesPageViewModel : ViewModel() {
    private val fetchDevicesUseCase: FetchDevicesUseCase by inject()
    private val startScrcpyUseCase: StartScrcpyUseCase by inject()
    private val stopScrcpyUseCase: StopScrcpyUseCase by inject()
    private val isRunningScrcpyUseCase: IsRunningScrcpyUseCase by inject()

    private val _devices: MutableStateFlow<List<Device>> = MutableStateFlow(emptyList())
    val devices: StateFlow<List<Device>> = _devices

    private val _states: MutableStateFlow<List<Pair<Device, Boolean>>> = MutableStateFlow(emptyList())
    val states: StateFlow<List<Pair<Device, Boolean>>> = _states

    override fun onStarted() {
        refresh()
    }

    fun refresh() {
        coroutineScope.launch {
            fetchDevices()
            fetchStates()
        }
    }

    fun startScrcpy(device: Device) {
        coroutineScope.launch {
            startScrcpyUseCase.execute(device, null) { fetchStates() }
            fetchStates()
        }
    }

    fun stopScrcpy(device: Device) {
        coroutineScope.launch {
            stopScrcpyUseCase.execute(device)
            fetchStates()
        }
    }

    private fun fetchDevices() {
        val devices = fetchDevicesUseCase.execute()
        _devices.value = devices
    }

    private fun fetchStates() {
        val states = _devices.value.map { device -> device to isRunningScrcpyUseCase.execute(device) }
        _states.value = states
    }
}