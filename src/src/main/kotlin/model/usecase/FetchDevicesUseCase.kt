package model.usecase

import model.entity.Device
import model.repository.DeviceRepository

class FetchDevicesUseCase(private val deviceRepository: DeviceRepository) {
    fun execute(): List<Device> {
        return deviceRepository.getAll()
    }
}