package view.pages.devices

import model.entity.Device
import model.repository.ProcessStatus

sealed class DevicesPageState {
    object Loading : DevicesPageState()

    object DeviceIsEmpty : DevicesPageState()

    data class DeviceExist(val devices: List<DeviceStatus>) : DevicesPageState()

    object Error : DevicesPageState()
}

data class DeviceStatus(val context: Device.Context, val processStatus: ProcessStatus)
