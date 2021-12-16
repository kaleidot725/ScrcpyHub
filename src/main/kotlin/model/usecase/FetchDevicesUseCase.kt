package model.usecase

import model.entity.Device
import model.repository.DeviceRepository

class FetchDevicesUseCase(private val deviceRepository: DeviceRepository) {
    suspend fun execute(): List<Device.Context> {
        return deviceRepository.getAll()
    }
}
