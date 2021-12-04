package model.usecase

import model.entity.Device
import model.repository.DeviceRepository

class UpdateDeviceNameUseCase(private val deviceRepository: DeviceRepository) {
    suspend fun execute(device: Device, newName: String, maxSize: Int?): Boolean {
        return try {
            deviceRepository.saveDeviceSetting(device, newName, maxSize)
            true
        } catch (e: Exception) {
            false
        }
    }
}
