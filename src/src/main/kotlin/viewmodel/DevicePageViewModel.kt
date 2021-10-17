package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.entity.Device

class DevicePageViewModel : ViewModel() {
    private val _device: MutableStateFlow<Device> = MutableStateFlow(Device("ID", "GOOD"))
    val device: StateFlow<Device> = _device

    override fun onStarted() {
        super.onStarted()
    }

    fun updateName(name: String) {

    }
}