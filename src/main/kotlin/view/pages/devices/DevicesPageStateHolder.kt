package view.pages.devices

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.entity.Device
import model.entity.Message
import model.usecase.FetchDevicesUseCase
import model.usecase.GetErrorMessageFlowUseCase
import model.usecase.GetNotifyMessageFlowUseCase
import model.usecase.GetScrcpyStatusUseCase
import model.usecase.SaveScreenshotUseCase
import model.usecase.StartScrcpyRecordUseCase
import model.usecase.StartScrcpyUseCase
import model.usecase.StopScrcpyRecordUseCase
import model.usecase.StopScrcpyUseCase
import view.StateHolder

class DevicesPageStateHolder(
    private val fetchDevicesUseCase: FetchDevicesUseCase,
    private val startScrcpyUseCase: StartScrcpyUseCase,
    private val stopScrcpyUseCase: StopScrcpyUseCase,
    private val startScrcpyRecordUseCase: StartScrcpyRecordUseCase,
    private val stopScrcpyRecordUseCase: StopScrcpyRecordUseCase,
    private val getScrcpyProcessStatusUseCase: GetScrcpyStatusUseCase,
    private val saveScreenshotToDesktop: SaveScreenshotUseCase,
    private val getNotifyMessageFlowUseCase: GetNotifyMessageFlowUseCase,
    private val getErrorMessageFlowUseCase: GetErrorMessageFlowUseCase
) : StateHolder() {
    private val deviceStatusList: MutableStateFlow<List<DeviceStatus>> = MutableStateFlow(emptyList())
    val states: StateFlow<DevicesPageState> = deviceStatusList.map { devices ->
        return@map if (devices.isNotEmpty()) {
            DevicesPageState.DeviceExist(devices)
        } else {
            DevicesPageState.DeviceIsEmpty
        }
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), DevicesPageState.Loading)

    private val notifyMessage: MutableStateFlow<List<Message.Notify>> = MutableStateFlow(emptyList())
    private val errorMessage: StateFlow<Set<Message.Error>> = getErrorMessageFlowUseCase().stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(),
        emptySet()
    )
    val messages: StateFlow<List<Message>> = combine(errorMessage, notifyMessage) { error, notify ->
        notify + error
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), emptyList())

    override fun onStarted() {
        observeNotifyMessage()
        coroutineScope.launch {
            while (true) {
                fetchStates()
                delay(2000)
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
        val devices = fetchDevicesUseCase.execute()
        updateStates(devices)
    }

    private fun updateStates(contextList: List<Device.Context>) {
        deviceStatusList.value = contextList.map { context ->
            DeviceStatus(context, getScrcpyProcessStatusUseCase.execute(context))
        }
    }

    private fun observeNotifyMessage() {
        coroutineScope.launch {
            getNotifyMessageFlowUseCase().collectLatest { newNotifyMessage ->
                coroutineScope.launch {
                    notifyMessage.value = notifyMessage.value.toMutableList().apply { add(newNotifyMessage) }
                    delay(2000)
                    notifyMessage.value = notifyMessage.value.toMutableList().apply { remove(newNotifyMessage) }
                }
            }
        }
    }
}
