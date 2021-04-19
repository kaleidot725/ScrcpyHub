package model.usecase

import model.entity.Device
import model.repository.DeviceRepository

class SelectDeviceUseCase(private val deviceRepository: DeviceRepository) {
    fun execute(device: Device) {
        deviceRepository.selected = device
    }
}
