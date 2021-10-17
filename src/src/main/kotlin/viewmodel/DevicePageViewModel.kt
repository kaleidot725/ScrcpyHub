package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.entity.Device

class DevicePageViewModel(private val device: Device) : ViewModel() {
    private val _titleName: MutableStateFlow<String> = MutableStateFlow(device.name)
    val titleName: StateFlow<String> = _titleName

    private val _name: MutableStateFlow<String> = MutableStateFlow(device.name)
    val name: StateFlow<String> = _name

    override fun onStarted() {
        super.onStarted()
    }

    fun updateName(name: String) {
        coroutineScope.launch {
            _name.emit(name)
        }
    }

    fun save() {
        // TODO
    }
}