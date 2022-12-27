package model.usecase

import model.entity.Device
import model.repository.DeviceRepository
import model.service.AdbServerService

class FetchDevicesUseCase(private val deviceRepository: DeviceRepository) {
    suspend fun execute(): List<Device.Context> {
        if (!AdbServerService.isRunning) return emptyList()
        return deviceRepository.getAll()
    }
}
