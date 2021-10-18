package model.usecase

import model.entity.Device
import model.repository.DeviceRepository

class UpdateDeviceNameUseCase(private val deviceRepository: DeviceRepository) {
    fun execute(device: Device, newName: String): Boolean {
        return try {
            deviceRepository.rename(device, newName)
            true
        } catch (e: Exception) {
            false
        }
    }
}