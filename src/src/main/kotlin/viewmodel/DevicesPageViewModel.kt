package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.entity.Device
import model.usecase.FetchDevicesUseCase
import model.usecase.IsScrcpyRunningUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyUseCase
import org.koin.core.component.inject

class DevicesPageViewModel : ViewModel() {
    private val fetchDevicesUseCase: FetchDevicesUseCase by inject()
    private val startScrcpyUseCase: StartScrcpyUseCase by inject()
    private val stopScrcpyUseCase: StopScrcpyUseCase by inject()
    private val isRunningScrcpyUseCase: IsScrcpyRunningUseCase by inject()

    private val _states: MutableStateFlow<List<Pair<Device, Boolean>>> = MutableStateFlow(emptyList())
    val states: StateFlow<List<Pair<Device, Boolean>>> = _states

    override fun onStarted() {
        coroutineScope.launch {
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
    
    private fun fetchStates() {
        val devices = fetchDevicesUseCase.execute()
        val states = devices.map { device -> device to isRunningScrcpyUseCase.execute(device) }
        _states.value = states
    }
}