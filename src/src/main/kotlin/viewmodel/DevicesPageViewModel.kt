package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.entity.Device
import model.usecase.FetchDevicesUseCase
import model.usecase.IsScrcpyRunningUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyUseCase

class DevicesPageViewModel(
    private val fetchDevicesUseCase: FetchDevicesUseCase,
    private val startScrcpyUseCase: StartScrcpyUseCase,
    private val stopScrcpyUseCase: StopScrcpyUseCase,
    private val isRunningScrcpyUseCase: IsScrcpyRunningUseCase
) : ViewModel() {
    private val _states: MutableStateFlow<List<Pair<Device, Boolean>>> = MutableStateFlow(emptyList())
    val states: StateFlow<List<Pair<Device, Boolean>>> = _states

    override fun onStarted() {
        refresh()
    }

    fun refresh() {
        coroutineScope.launch {
            fetchStates()
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
        val devices = fetchDevicesUseCase.execute()
        val states = devices.map { device -> device to isRunningScrcpyUseCase.execute(device) }
        _states.value = states
    }
}