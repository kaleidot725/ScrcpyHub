package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.entity.Device
import model.usecase.UpdateDeviceNameUseCase

class DevicePageViewModel(
    private val device: Device,
    private val updateDeviceNameUseCase: UpdateDeviceNameUseCase
) : ViewModel() {
    private val _titleName: MutableStateFlow<String> = MutableStateFlow(device.displayName)
    val titleName: StateFlow<String> = _titleName

    private val _editName: MutableStateFlow<String> = MutableStateFlow(device.displayName)
    val editName: StateFlow<String> = _editName

    private val _maxSize: MutableStateFlow<String> = MutableStateFlow(device.setting.maxSize?.toString() ?: "")
    val maxSize: StateFlow<String> = _maxSize

    private val _maxSizeError: MutableStateFlow<String> = MutableStateFlow("")
    val maxSizeError: StateFlow<String> = _maxSizeError

    private val _enableRecording: MutableStateFlow<Boolean> = MutableStateFlow(device.setting.enableRecording)
    val enableRecording: StateFlow<Boolean> = _enableRecording

    private val _savable: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val savable: StateFlow<Boolean> = _savable

    fun updateName(name: String) {
        coroutineScope.launch {
            _editName.emit(name)
            validate()
        }
    }

    fun updateMaxSize(maxSize: String) {
        coroutineScope.launch {
            _maxSize.emit(maxSize)
            validate()
        }
    }

    fun updateEnableRecording(enableRecording: Boolean) {
        coroutineScope.launch {
            _enableRecording.emit(enableRecording)
        }
    }

    fun save() {
        coroutineScope.launch {
            val setting = Device.Setting(
                customName = _editName.value,
                maxSize = _maxSize.value.toIntOrNull(),
                enableRecording = _enableRecording.value
            )

            updateDeviceNameUseCase.execute(device, setting)
            _titleName.value = _editName.value
        }
    }

    private suspend fun validate() {
        val maxSizeError = _maxSize.value.isNotEmpty() && _maxSize.value.toIntOrNull() == null
        val maxSizeErrorMessage = if (maxSizeError) "Please MaxSize as a number" else ""
        _maxSizeError.emit(maxSizeErrorMessage)

        val hasError = maxSizeError
        _savable.emit(!hasError)
    }
}