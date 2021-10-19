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

    private val _maxSize: MutableStateFlow<String> = MutableStateFlow(device.maxSize?.toString() ?: "")
    val maxSize: StateFlow<String> = _maxSize

    fun updateName(name: String) {
        coroutineScope.launch {
            _editName.emit(name)
        }
    }

    fun updateMaxSize(maxSize: String) {
        coroutineScope.launch {
            _maxSize.emit(maxSize)
        }
    }

    fun save() {
        coroutineScope.launch {
            updateDeviceNameUseCase.execute(device, _editName.value, _maxSize.value.toIntOrNull())
            _titleName.value = _editName.value
        }
    }
}